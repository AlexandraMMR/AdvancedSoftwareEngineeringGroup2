package uk.ac.bath.cm50286.group2.newbank.server.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.bath.cm50286.group2.newbank.server.dao.*;
import uk.ac.bath.cm50286.group2.newbank.server.model.Account;
import uk.ac.bath.cm50286.group2.newbank.server.model.Customer;
import uk.ac.bath.cm50286.group2.newbank.server.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public class TransactionController {
  private static final Logger LOGGER = LogManager.getLogger(TransactionController.class);

  //  private AccountDAO accountDAO = new AccountDAO();
//  private CustomerDAO customerDAO;
//  private AccountController accountController = new AccountController(accountDAO);
  private TransactionDAO transactionDAO = new TransactionDAO();
  private TransTypeDAO transTypeDAO = new TransTypeDAO();

  public String createTransaction(Customer customer, String transtype, int transfrom, int transto, BigDecimal amount) {
    LOGGER.info("Creating transaction From: " + transfrom + "  To: " + transto + " Amount" + amount);
    int transtypeid = checkTransType(transtype);
    transactionDAO.insertTransaction(transtypeid, transfrom, transto, amount);

    return "Transaction created from Acct ID: " + transfrom + "  To Acct ID: " + transto + " Amount: " + amount;

  }

  private int checkTransType(String transtype) {
    if (transTypeDAO.getTransTypeID(transtype) > 0) {
      return transTypeDAO.getTransTypeID(transtype);
    } else {
      return 0;
    }
  }

  public String getTransactions(Customer customer) {
    List<Transaction> transList;
    if (!customer.getUsername().equals("admin")) {
      transList = transactionDAO.getTransactions(customer);
    } else {
      transList = transactionDAO.getAllTransactions();
    }
    LOGGER.info("Found " + transList.size() + " transactions.");
    StringBuilder sb = new StringBuilder();
    for (Transaction transaction : transList) {
      sb.append(transaction.toString());
    }
    return sb.toString();
  }

}