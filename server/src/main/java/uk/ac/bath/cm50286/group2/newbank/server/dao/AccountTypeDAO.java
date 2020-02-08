package uk.ac.bath.cm50286.group2.newbank.server.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.bath.cm50286.group2.newbank.server.model.AccountType;
import uk.ac.bath.cm50286.group2.newbank.server.model.Customer;
import uk.ac.bath.cm50286.group2.newbank.server.util.DBUtils;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountTypeDAO {
    private static final Logger LOGGER = LogManager.getLogger(AccountDAO.class);

    private static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS accounttypes" +
            "(accttypeid INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
            "acctdesc VARCHAR(255) NOT NULL)";

    private static final String SQL_INSERT = "INSERT INTO accounttypes" +
            " (acctdesc) VALUES " +
            " (?);";

    private static final String SQL_SELECT_ALL = "SELECT * FROM accounttypes";

    private static final String SQL_SELECT_BY_ACCTYPEID = "SELECT * FROM accounttypes " +
            " WHERE accttypeid = ?;";

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
    public void insertAccountType(String acctdesc) {
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT);
            ps.setString(1, acctdesc);
            LOGGER.info("H2: "+ps.toString());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            DBUtils.printSQLException(e);
        }
    }


    public List<AccountType> getAllAccountTypes() {
        List<AccountType> allAccountTypes = new ArrayList<>();
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SQL_SELECT_ALL);
            LOGGER.info("H2: "+ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                allAccountTypes.add(new AccountType(
                        rs.getInt("accttypeid"),
                        rs.getString("acctdesc")
                ));
            }
            rs.close();
        }
        catch (SQLException e) {
            DBUtils.printSQLException(e);
        }
        return allAccountTypes;
    }

    public AccountType getAccountType(Integer acctid) {
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_ACCTYPEID);
            ps.setString(1, acctid.toString());
            LOGGER.info("H2: "+ps.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new AccountType(
                        rs.getInt("accttypeid"),
                        rs.getString("acctdesc")
                );
            }
            rs.close();
        }
        catch (SQLException e) {
            DBUtils.printSQLException(e);
        }
        return null;
    }

}
