package uk.ac.bath.cm50286.group2.newbank.server.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.bath.cm50286.group2.newbank.server.dao.TransTypeDAO;

public class TransTypeController {

  private static final Logger LOGGER = LogManager.getLogger(AccountController.class);
  private TransTypeDAO transTypeDAO;

  public TransTypeController(TransTypeDAO transTypeDAO) {
    this.transTypeDAO = transTypeDAO;
  }

  public String getTransDescByTypeID(int transTypeID) {
    return transTypeDAO.getTransDesc(transTypeID);
  }

  public int getTransTypeIDByDesc(String transDesc) {
    return transTypeDAO.getTransIDbyDesc(transDesc);
  }

}
