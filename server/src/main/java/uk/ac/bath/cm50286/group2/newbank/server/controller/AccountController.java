package uk.ac.bath.cm50286.group2.newbank.server.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.bath.cm50286.group2.newbank.server.dao.AccountDAO;
import uk.ac.bath.cm50286.group2.newbank.server.dao.AccountTypeDAO;
import uk.ac.bath.cm50286.group2.newbank.server.dao.TransTypeDAO;
import uk.ac.bath.cm50286.group2.newbank.server.model.Account;
import uk.ac.bath.cm50286.group2.newbank.server.model.Customer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static uk.ac.bath.cm50286.group2.newbank.server.util.StringUtils.convertToTitleCaseSplitting;

public class AccountController {

    private static final Logger LOGGER = LogManager.getLogger(AccountController.class);

    private AccountDAO accountDAO;
    private AccountTypeDAO accountTypeDAO;
    private TransactionController transactionController = new TransactionController();
    private TransTypeController transTypeController = new TransTypeController(new TransTypeDAO());

    public AccountController(final AccountDAO accountDAO, final AccountTypeDAO accountTypeDAO) {
        this.accountDAO = accountDAO;
        this.accountTypeDAO = accountTypeDAO;
    }

    public BigDecimal getBalance(int acctid) {
        return accountDAO.getAcctBalance(acctid);
    }

    public List<Account> getAccounts(Customer customer) {
        return accountDAO.getAccountsForCustomer(customer);
    }

    public List<Integer> getAcctID(Customer customer) {
        return accountDAO.getAcctIDforCustomer(customer);
    }

    public String getAccountsAsString(Customer customer) {
        List<Account> accountList = getAccounts(customer);
        LOGGER.info("Found " + accountList.size() + " accounts for customer " + customer.getUsername() + ".");
        StringBuilder sb = new StringBuilder();
        for (Account account : accountList) {
            sb.append(account.toString());
        }
        return sb.toString();
    }

    public String transfer(Customer customer, String transdesc, int transfrom, int transto, BigDecimal amount) {
        if (accountDAO.getCustIDforAcct(transfrom) == 0 || accountDAO.getCustIDforAcct(transto) == 0) {
            return "ERROR: Unknown account in to and/or from field.\n";
        } else if (!customer.getUsername().equals("admin") && !customer.getCustid().equals(accountDAO.getCustIDforAcct(transfrom))) {
            return "ERROR: Invalid Transfer User.\n";
        } else if (amount.compareTo(getBalance(transfrom)) > 1) {
            return "ERROR: Insufficient Funds.\n";
        } else {
            //TODO: What happens when a transfer happens?

            return "";
        }
    }

    public String payCustomer(Customer customer, int transfrom, int transto, BigDecimal amount) {
        if (!accountDAO.getAcctIDforCustomer(customer).contains(transfrom)) {
            return "ERROR: Incorrect source account number specified";

        } else if (amount.compareTo(getBalance(transfrom)) > 0) {
            return "ERROR: Insufficient Funds.\n";
        } else if (!validAccount(transto)) {
            return "ERROR: Incorrect destination account number specified";
        } else {
            accountDAO.withdrawfromAccount(transfrom, amount);
            accountDAO.depositToAccount(transto, amount);
            int transtypeid = transTypeController.getTransTypeIDByDesc("Pay");
            transactionController.createTransaction(customer, transtypeid, transfrom, transto, amount);
            return "Paid " + amount + " from Account: " + transfrom + " to Customer: " +
                    accountDAO.getCustIDforAcct(transto) + " Account: " +
                    transto + "\n";
        }
    }

    private boolean validAccount(int transto) {
        List<Account> accounts = new ArrayList<>(accountDAO.getAllAccounts());
        for (Account account : accounts) {
            if (account.getAcctID() == transto) {
                return true;
            }
        }
        return false;
    }

    public String deposit(Customer customer, int acctid, BigDecimal amount) {
        if (!customer.getUsername().equals("admin")) {
            return "Only Admin can deposit";
        } else if (accountDAO.getCustIDforAcct(acctid) == 0) {
            return "Invalid acct ID";
        } else {
            if (accountDAO.depositToAccount(acctid, amount)) {
                int transtypeid = transTypeController.getTransTypeIDByDesc("Deposit");
                transactionController.createTransaction(customer, transtypeid, 1, acctid, amount);
                return amount + " added to Acct ID:" + acctid + "\n";
            }

            return "Unable to add amount:" + amount + "to account: " + acctid;
        }
    }

    public String getAllAccounts(Customer customer) {
        if (!customer.getUsername().equals("admin")) {
            return "Only Admin can list all accounts";
        } else {
            List<Account> accountList = accountDAO.getAllAccounts();
            LOGGER.info("Found " + accountList.size() + " accounts.");
            StringBuilder sb = new StringBuilder();
            for (Account account : accountList) {
                sb.append(account.toString());
            }
            return sb.toString();
        }
    }

    public String createAccount(final Customer customer, final String accountName) {
        requireNonNull(customer, "FAIL - Customer must not be null");
        final String titleCaseAccount = convertToTitleCaseSplitting(accountName);
        if (accountTypeDAO.getAllAccountTypes().stream().noneMatch(accountType -> accountType.getAcctdesc().contains(titleCaseAccount))) {
            throw new IllegalArgumentException("FAIL - Invalid command");
        }
        if (getAccounts(customer).stream().anyMatch(account -> account.getAccountName().equals(titleCaseAccount))) {
            throw new IllegalArgumentException("FAIL - Account already exists");
        }
        accountDAO.insertAccount(customer, accountTypeDAO.getAccountTypeID(titleCaseAccount));
        return "SUCCESS";
    }
}