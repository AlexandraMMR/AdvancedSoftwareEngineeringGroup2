package uk.ac.bath.cm50286.group2.newbank.server.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.bath.cm50286.group2.newbank.server.dao.AccountDAO;
import uk.ac.bath.cm50286.group2.newbank.server.dao.AccountTypeDAO;
import uk.ac.bath.cm50286.group2.newbank.server.model.Account;
import uk.ac.bath.cm50286.group2.newbank.server.model.AccountType;
import uk.ac.bath.cm50286.group2.newbank.server.model.Customer;

import java.util.List;

public class AccountTypeController {

    private static final Logger LOGGER = LogManager.getLogger(AccountController.class);

    private AccountTypeDAO accountTypeDAO;

    public AccountTypeController(AccountTypeDAO accountTypeDAO) {
        this.accountTypeDAO = accountTypeDAO;
    }


  public String getAllAccountTypes(Customer customer) {
    List<AccountType> accountTypeList = accountTypeDAO.getAllAccountTypes();
    LOGGER.info("Found "+accountTypeList.size()+" Account Types.");
    StringBuilder sb = new StringBuilder();
    for (AccountType accountType : accountTypeList) {
        sb.append(accountType.toString());
    }
    return sb.toString();
    }



 /*   public String getAccountsAsString(Customer customer) {
        List<Account> accountList = getAccounts(customer);
        LOGGER.info("Found "+accountList.size()+" accounts for customer "+customer.getUsername()+".");
        StringBuilder sb = new StringBuilder();
        for (Account account : accountList) {
            sb.append(account.toString());
        }
        return sb.toString();
    }*/


}
