package uk.ac.bath.cm50286.group2.newbank.server.dao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.bath.cm50286.group2.newbank.server.*;
import uk.ac.bath.cm50286.group2.newbank.server.util.DBUtils;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransTypeDAO {

  private static final Logger LOGGER = LogManager.getLogger(TransTypeDAO.class);

  private static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS TRANSTYPE " +
      "(transtypeid INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
      "transtypedesc VARCHAR(255))";
  private static final String SQL_INSERT = "INSERT INTO transtype" +
      " (transtypedesc) VALUES " +
      " (?);";
  private static final String SQL_TRANSTYPEID = "SELECT TRANSTYPEID FROM TRANSTYPE " +
      "WHERE TRANSDESC = ?";
      ;

  public void createTable() {
    try (Connection connection = DBUtils.getConnection()) {
      Statement statement = connection.createStatement();
      LOGGER.info("H2: " + SQL_CREATE);
      statement.execute(SQL_CREATE);
    } catch (SQLException e) {
      DBUtils.printSQLException(e);
    }
  }

  public void insertTransType(String transtypedesc) {
    try (Connection connection = DBUtils.getConnection()) {
      PreparedStatement ps = connection.prepareStatement(SQL_INSERT);
      ps.setString(1, transtypedesc);
      LOGGER.info("H2: "+ps.toString());
      ps.executeUpdate();
    }
    catch (SQLException e) {
      DBUtils.printSQLException(e);
    }
  }

  public int getTransTypeID(String transdesc) {
    try (Connection connection = DBUtils.getConnection()) {
      PreparedStatement ps = connection.prepareStatement(SQL_TRANSTYPEID);
      ps.setString(1, transdesc);
      LOGGER.info("H2: " + ps.toString());
      ResultSet rs = ps.executeQuery();
      rs.next();
      return rs.getInt("transtypeid");
    } catch (SQLException e) {
      DBUtils.printSQLException(e);
    }

    return 0;
  }


}