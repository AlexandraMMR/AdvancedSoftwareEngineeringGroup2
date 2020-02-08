package uk.ac.bath.cm50286.group2.newbank.server.dao;

/**
 * Customer Database Objects
 * Columns defined by database:
 * CustID - Customer ID number
 * FirstName
 * LastName
 * UserName
 * Password
 * Email
 * Address
 * PostCode
 * NI_Number
 *
 */



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
        "(custid INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
        " firstname VARCHAR(255) NOT NULL," +
        " lastname VARCHAR(255) NOT NULL," +
        " username VARCHAR(255) NOT NULL," +
        " password VARCHAR(255) NOT NULL," +
        " email VARCHAR(255) NOT NULL, " +
        " address VARCHAR(255) NOT NULL, " +
        " postcode VARCHAR(255) NOT NULL, " +
        " ninumber VARCHAR(255) NOT NULL)"
        ;
    private static final String SQL_SELECT_ALL = "SELECT * FROM customers";
    private static final String SQL_SELECT_BY_USERNAME = "SELECT * FROM customers " +
            " WHERE username = ?;";
    private static final String SQL_INSERT = "INSERT INTO customers" +
            " (firstname, lastname, userName, password, email, address, postcode, ninumber) VALUES " +
            " (?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_UPDATE = "UPDATE customers SET " +
            " password = ?, " +
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
                allCustomers.add(new Customer(
                    rs.getInt("custid"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("address"),
                    rs.getString("postcode"),
                    rs.getString("ninumber")

                ));
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
                return new Customer(
                    rs.getInt("custid"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("address"),
                    rs.getString("postcode"),
                    rs.getString("ninumber")
                );
            }
            rs.close();
        }
        catch (SQLException e) {
            DBUtils.printSQLException(e);
        }
        return null;
    }

    public void insertCustomer(String firstname, String lastname, String username,String password,
                               String email, String address, String postcode, String ninumber ) {
        try (Connection connection = DBUtils.getConnection()) { 
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT);
            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, username);
            ps.setString(4, password);
            ps.setString(5, email);
            ps.setString(6, address);
            ps.setString(7, postcode);
            ps.setString(8, ninumber);

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
            ps.setString(2, username);
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
