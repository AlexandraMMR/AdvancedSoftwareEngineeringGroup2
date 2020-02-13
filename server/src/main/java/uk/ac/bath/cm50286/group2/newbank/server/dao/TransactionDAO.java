package uk.ac.bath.cm50286.group2.newbank.server.dao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.bath.cm50286.group2.newbank.server.util.DBUtils;

import java.math.BigDecimal;
import java.sql.*;

public class TransactionDAO {

  private static final Logger LOGGER = LogManager.getLogger(TransactionDAO.class);

  private static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS transaction" +
      "(transid INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
      "transtypeid INT," +
      "transfrom INT," +
      "transto INT," +
      "amount NUMERIC(10,2)," +
      " FOREIGN KEY (transtypeid) REFERENCES transtype(transtypeid)," +
      " FOREIGN KEY (transfrom) REFERENCES customers(custid)," +
      " FOREIGN KEY (transto) REFERENCES customers(custid)," +
      " balance NUMERIC(10,2));";

  private static final String SQL_INSERT = "INSERT INTO transaction" +
      " (transtypeid, transfrom, transto, amount) VALUES " +
      " (?, ?, ?, ?);";





  public void createTable() {
    try (Connection connection = DBUtils.getConnection()) {
      Statement statement = connection.createStatement();
      LOGGER.info("H2: "+SQL_CREATE);
      statement.execute(SQL_CREATE);
    }
    catch (SQLException e) {
      DBUtils.printSQLException(e);
    }
  }



  public void insertTransaction(int transtypeid, int transfrom, int transto, BigDecimal amount){
    try (Connection connection = DBUtils.getConnection()) {
      PreparedStatement ps = connection.prepareStatement(SQL_INSERT);
      ps.setInt (1, transtypeid);
      ps.setInt(2, transfrom);
      ps.setInt(3, transto);
      ps.setBigDecimal(4, amount);

      LOGGER.info("H2: "+ps.toString());
      ps.executeUpdate();
    }
    catch (SQLException e) {
      DBUtils.printSQLException(e);
    }


  }


}



