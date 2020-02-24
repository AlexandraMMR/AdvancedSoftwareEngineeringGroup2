package uk.ac.bath.cm50286.group2.newbank.server.dao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.bath.cm50286.group2.newbank.server.model.Account;
import uk.ac.bath.cm50286.group2.newbank.server.model.Customer;
import uk.ac.bath.cm50286.group2.newbank.server.model.Transaction;
import uk.ac.bath.cm50286.group2.newbank.server.util.DBUtils;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

  private static final Logger LOGGER = LogManager.getLogger(TransactionDAO.class);

  private static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS transaction" +
      "(transid INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
      "transtypeid INT," +
      "transfrom INT," +
      "transto INT," +
      "amount NUMERIC(10,2)," +
      " FOREIGN KEY (transtypeid) REFERENCES transtype(transtypeid)," +
      " FOREIGN KEY (transfrom) REFERENCES account(acctid)," +
      " FOREIGN KEY (transto) REFERENCES account(acctid));";

  private static final String SQL_INSERT = "INSERT INTO transaction" +
      " (transtypeid, transfrom, transto, amount) VALUES " +
      " (?, ?, ?, ?);";
  private static final String SQL_SELECT_ALL = "SELECT * from TRANSACTION;";


  public void createTable() {
    try (Connection connection = DBUtils.getConnection()) {
      Statement statement = connection.createStatement();
      LOGGER.info("H2: " + SQL_CREATE);
      statement.execute(SQL_CREATE);
    } catch (SQLException e) {
      DBUtils.printSQLException(e);
    }
  }

  public void insertTransaction(int transtypeid, int transfrom, int transto, BigDecimal amount) {
    try (Connection connection = DBUtils.getConnection()) {
      PreparedStatement ps = connection.prepareStatement(SQL_INSERT);
      ps.setInt(1, transtypeid);
      ps.setInt(2, transfrom);
      ps.setInt(3, transto);
      ps.setBigDecimal(4, amount);

      LOGGER.info("H2: " + ps.toString());
      ps.executeUpdate();
    } catch (SQLException e) {
      DBUtils.printSQLException(e);
    }

  }

  public List<Transaction> getAllTransactions() {

    List<Transaction> allTransactions = new ArrayList<>();
    try (Connection connection = DBUtils.getConnection()) {
      PreparedStatement ps = connection.prepareStatement(SQL_SELECT_ALL);
      LOGGER.info("H2: " + ps.toString());
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        allTransactions.add(new Transaction(
            rs.getInt("transactionid"),
            rs.getInt("trantypeid"),
            rs.getInt("transfrom"),
            rs.getInt("transto"),
            rs.getBigDecimal("amount")));
      }
      rs.close();
    } catch (SQLException e) {
      DBUtils.printSQLException(e);
    }
    return allTransactions;

  }

  public List<Transaction> getTransactions(Customer customer) {
    return null;
  }
}



