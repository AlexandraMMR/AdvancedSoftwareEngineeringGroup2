package uk.ac.bath.cm50286.group2.newbank.server.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.bath.cm50286.group2.newbank.server.dao.*;
import uk.ac.bath.cm50286.group2.newbank.server.model.AccountType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {

    private static final Logger LOGGER = LogManager.getLogger(DBUtils.class);

    // JDBC driver and database URL
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:";
    private static final String DB_FILE = "~/newbank";

    // database credentials
    private static final String DB_USER = "";
    private static final String DB_PASS = "";

    public static Connection getConnection() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        try {
            LOGGER.info("Connecting to database...");
            connection = DriverManager.getConnection(DB_URL+DB_FILE, DB_USER, DB_PASS);
        } catch (SQLException e) {
            LOGGER.error("SQL Exception: State " + e.getSQLState() + ", Error Code " + e.getErrorCode() + ", " + e.getMessage());
        }
        return connection;
    }

    public static String getDbFile() {
        return DB_FILE;
    }

    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                LOGGER.error("SQLState: " + ((SQLException) e).getSQLState());
                LOGGER.error("Error Code: " + ((SQLException) e).getErrorCode());
                LOGGER.error("Message: " + e.getMessage());
            }
        }
    }

    // This method creates all the tables in the DB file specified above; do this one time to set up a new environment
    public static void main(String[] args) {
        CustomerDAO customerDao = new CustomerDAO();
        customerDao.createTable();
        // create admin user
        customerDao.insertCustomer("admin","admin","admin","admin",
            "admin@admin.admin", "5 Admin Avenue", "AD5M1N", "AD123456789");

        AccountDAO accountDao = new AccountDAO();
        accountDao.createTable();

        AccountTypeDAO accountTypeDao = new AccountTypeDAO();
        accountTypeDao.createTable();
        accountTypeDao.insertAccountType("Main");
        accountTypeDao.insertAccountType("Savings");
        accountTypeDao.insertAccountType("ISA");


        TransTypeDAO transTypeDAO = new TransTypeDAO();
        transTypeDAO.createTable();
        transTypeDAO.insertTransType("Deposit");
        transTypeDAO.insertTransType("Withdrawal");
        transTypeDAO.insertTransType("Transfer");
        transTypeDAO.insertTransType("Loan");

        TransactionDAO transactionDAO = new TransactionDAO();
        transactionDAO.createTable();






    }

}
