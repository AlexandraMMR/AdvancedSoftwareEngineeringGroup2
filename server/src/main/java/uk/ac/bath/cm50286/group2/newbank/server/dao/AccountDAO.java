package uk.ac.bath.cm50286.group2.newbank.server.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.bath.cm50286.group2.newbank.server.model.Account;
import uk.ac.bath.cm50286.group2.newbank.server.model.Customer;
import uk.ac.bath.cm50286.group2.newbank.server.util.DBUtils;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    private static final Logger LOGGER = LogManager.getLogger(AccountDAO.class);

    private static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS accounts" +
            "(customer VARCHAR(255) NOT NULL," +
            " name VARCHAR(255) NOT NULL ," +
            " balance NUMERIC(10,2), " +
            " PRIMARY KEY (customer,name)," +
            " FOREIGN KEY (customer) REFERENCES customers(username))";
    private static final String SQL_SELECT_BY_CUSTOMER = "SELECT * FROM accounts " +
            " WHERE customer = ?;";
    private static final String SQL_INSERT = "INSERT INTO accounts" +
            " (customer, name, balance) VALUES " +
            " (?, ?, ?);";
    private static final String SQL_UPDATE = "UPDATE accounts SET " +
            " balance = ? " +
            " WHERE customer = ? " +
            " AND name = ?;";
    private static final String SQL_DELETE = "DELETE FROM accounts " +
            " WHERE customer = ? " +
            " AND name = ?;";

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

    public List<Account> getAccountsForCustomer(Customer customer) {
        List<Account> accounts = new ArrayList<>();
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_CUSTOMER);
            ps.setString(1, customer.getUsername());
            LOGGER.info("H2: "+ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                accounts.add(new Account(customer,rs.getString("name"),rs.getBigDecimal("balance")));
            }
            rs.close();
        }
        catch (SQLException e) {
            DBUtils.printSQLException(e);
        }
        return accounts;
    }

    public void insertAccount(Customer customer,String name) {
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT);
            ps.setString(1, customer.getUsername());
            ps.setString(2, name);
            ps.setBigDecimal(3,new BigDecimal(0));
            LOGGER.info("H2: "+ps.toString());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            DBUtils.printSQLException(e);
        }
    }

    public void updateAccount(Customer customer,String name,BigDecimal balance) {
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SQL_UPDATE);
            ps.setBigDecimal(1,new BigDecimal(0));
            ps.setString(2, customer.getUsername());
            ps.setString(3, name);
            LOGGER.info("H2: "+ps.toString());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            DBUtils.printSQLException(e);
        }
    }

    public void deleteAccount(Customer customer,String name) {
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SQL_DELETE);
            ps.setString(1, customer.getUsername());
            ps.setString(2, name);
            LOGGER.info("H2: "+ps.toString());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            DBUtils.printSQLException(e);
        }
    }

}
