package uk.ac.bath.cm50286.group2.newbank.server.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.bath.cm50286.group2.newbank.server.dao.*;
import uk.ac.bath.cm50286.group2.newbank.server.model.Customer;

import java.math.BigDecimal;

public class TransactionController {
  private static final Logger LOGGER = LogManager.getLogger(TransactionController.class);

  private AccountDAO accountDAO = new AccountDAO();
  private CustomerDAO customerDAO;
  private AccountController accountController = new AccountController(accountDAO);
  private TransactionDAO tranactionDAO = new TransactionDAO();
  private TransTypeDAO transTypeDAO = new TransTypeDAO();

  public String createTransaction(Customer customer, String transtype, int transfrom, int transto, BigDecimal amount) {
    LOGGER.info("Creating transaction From: " + transfrom + "  To: " + transto + " Amount" + amount);

    if (accountDAO.getCustIDforAcct(transfrom) == 0 || accountDAO.getCustIDforAcct(transto) == 0) {
      return "ERROR: Unknown account in to and/or from field.";
    } else if (!customer.getUsername().equals("admin") && !customer.getCustid().equals(accountDAO.getCustIDforAcct(transfrom))) {
      return "ERROR: Invalid Transfer User.";
    } else if (amount.compareTo(accountController.getBalance(transfrom)) > 1) {
      return "ERROR: Insufficient Funds.";
    } else {

      int transtypeid = checkTransType(transtype);
      tranactionDAO.insertTransaction(transtypeid, transfrom, transto, amount);

      return "Transaction created from Acct ID: " + transfrom + "  To Acct ID: " + transto + " Amount: " + amount;
    }
  }

  private int checkTransType(String transtype) {
    if (transTypeDAO.getTransTypeID(transtype) > 0) {
      return transTypeDAO.getTransTypeID(transtype);
    } else {
      return 0;
    }
  }
}