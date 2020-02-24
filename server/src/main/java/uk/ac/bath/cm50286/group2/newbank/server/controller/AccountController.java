package uk.ac.bath.cm50286.group2.newbank.server.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.bath.cm50286.group2.newbank.server.dao.AccountDAO;
import uk.ac.bath.cm50286.group2.newbank.server.model.Account;
import uk.ac.bath.cm50286.group2.newbank.server.model.Customer;

import java.math.BigDecimal;
import java.util.List;

public class AccountController {

    private static final Logger LOGGER = LogManager.getLogger(AccountController.class);

    private AccountDAO accountDAO;
    private TransactionController transactionController;

    public AccountController(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
        this.transactionController = transactionController;
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

    public String payCustomer (Customer customer, int transfrom, int transto, BigDecimal amount){
        if(amount.compareTo(getBalance(transfrom)) > 1 ){
            return "ERROR: Insufficient Funds.\n";
        }  else{
            accountDAO.depositToAccount(transto,amount);
            transactionController.createTransaction(customer,"Payment",transfrom,transto,amount);
            return amount + " paid by " + customer.getFirstName() + " from Acct ID:" + transfrom + " toAcct ID:" +
                    transto + "\n";
        }
    }

    public String deposit(Customer customer, int acctid, BigDecimal amount) {
        if (!customer.getUsername().equals("admin")) {
            return "Only Admin can deposit";
        } else if (accountDAO.getCustIDforAcct(acctid) == 0) {
            return "Invalid acct ID";
        } else {
            accountDAO.depositToAccount(acctid, amount);
            transactionController.createTransaction(customer,"Deposit",-1,acctid,amount);
            return amount + " added to Acct ID:" + acctid + "\n";
        }
    }

    public String getAllAccounts(Customer customer) {
        if (!customer.getUsername().equals("admin")) {
            return "Only Admin can list all accounts";
        }
        else {
            List<Account> accountList = accountDAO.getAllAccounts();
            LOGGER.info("Found " + accountList.size() + " accounts.");
            StringBuilder sb = new StringBuilder();
            for (Account account : accountList) {
                sb.append(account.toString());
            }
            return sb.toString();
        }
    }

}