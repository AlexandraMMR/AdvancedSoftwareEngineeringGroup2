package uk.ac.bath.cm50286.group2.newbank.server.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.bath.cm50286.group2.newbank.server.dao.AccountDAO;
import uk.ac.bath.cm50286.group2.newbank.server.model.Account;
import uk.ac.bath.cm50286.group2.newbank.server.model.Customer;

import java.util.List;

public class AccountController {

    private static final Logger LOGGER = LogManager.getLogger(AccountController.class);

    private AccountDAO accountDAO;

    public AccountController(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public List<Account> getAccounts(Customer customer) {
        return accountDAO.getAccountsForCustomer(customer);
    }

    public String getAccountsAsString(Customer customer) {
        List<Account> accountList = getAccounts(customer);
        LOGGER.info("Found "+accountList.size()+" accounts for customer "+customer.getUsername()+".");
        StringBuilder sb = new StringBuilder();
        for (Account account : accountList) {
            sb.append(account.toString());
        }
        return sb.toString();
    }

}
