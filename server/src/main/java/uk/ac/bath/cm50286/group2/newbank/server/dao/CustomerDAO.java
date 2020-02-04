package uk.ac.bath.cm50286.group2.newbank.server.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.bath.cm50286.group2.newbank.server.model.Customer;
import uk.ac.bath.cm50286.group2.newbank.server.util.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    private static final Logger LOGGER = LogManager.getLogger(CustomerDAO.class);

    private static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS customers" +
            "(username VARCHAR(255) NOT NULL," +
            " password VARCHAR(255) ," +
            " name VARCHAR(255), " +
            " PRIMARY KEY (username))";
    private static final String SQL_SELECT_ALL = "SELECT * FROM customers";
    private static final String SQL_SELECT_BY_USERNAME = "SELECT * FROM customers " +
            " WHERE username = ?;";
    private static final String SQL_INSERT = "INSERT INTO customers" +
            " (username, password, name) VALUES " +
            " (?, ?, ?);";
    private static final String SQL_UPDATE = "UPDATE customers SET " +
            " password = ?, " +
            " name = ? " +
            " WHERE username = ?;";
    private static final String SQL_DELETE = "DELETE FROM customers " +
            " WHERE username = ?;";

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

    public List<Customer> getAllCustomers() {
        List<Customer> allCustomers = new ArrayList<>();
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SQL_SELECT_ALL);
            LOGGER.info("H2: "+ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                allCustomers.add(new Customer(rs.getString("username"),rs.getString("password"),rs.getString("name")));
            }
            rs.close();
        }
        catch (SQLException e) {
            DBUtils.printSQLException(e);
        }
        return allCustomers;
    }

    public Customer getCustomer(String username) {
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_USERNAME);
            ps.setString(1, username);
            LOGGER.info("H2: "+ps.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Customer(rs.getString("username"),rs.getString("password"),rs.getString("name"));
            }
            rs.close();
        }
        catch (SQLException e) {
            DBUtils.printSQLException(e);
        }
        return null;
    }

    public void insertCustomer(String username,String password,String name) {
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, name);
            LOGGER.info("H2: "+ps.toString());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            DBUtils.printSQLException(e);
        }
    }

    public void updateCustomer(String username,String password,String name) {
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SQL_UPDATE);
            ps.setString(1, password);
            ps.setString(2, name);
            ps.setString(3, username);
            LOGGER.info("H2: "+ps.toString());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            DBUtils.printSQLException(e);
        }
    }

    public void deleteCustomer(String username) {
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SQL_DELETE);
            ps.setString(1, username);
            LOGGER.info("H2: "+ps.toString());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            DBUtils.printSQLException(e);
        }
    }

}
