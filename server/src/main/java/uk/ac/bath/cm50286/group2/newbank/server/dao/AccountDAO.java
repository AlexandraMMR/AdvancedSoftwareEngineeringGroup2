package uk.ac.bath.cm50286.group2.newbank.server.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.bath.cm50286.group2.newbank.server.model.Account;
import uk.ac.bath.cm50286.group2.newbank.server.model.AccountType;
import uk.ac.bath.cm50286.group2.newbank.server.model.Customer;
import uk.ac.bath.cm50286.group2.newbank.server.dao.AccountTypeDAO;
import uk.ac.bath.cm50286.group2.newbank.server.util.DBUtils;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    private static final Logger LOGGER = LogManager.getLogger(AccountDAO.class);

    /*private static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS accounts" +
            "(customer VARCHAR(255) NOT NULL," +
            " name VARCHAR(255) NOT NULL ," +
            " balance NUMERIC(10,2), " +
            " PRIMARY KEY (customer,name)," +
            " FOREIGN KEY (customer) REFERENCES customers(username))";*/
    private static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS accounts" +
            "(acctid INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
            "custid INT," +
            "accttypeid INT," +
            " FOREIGN KEY (custid) REFERENCES customers(custid)," +
            " FOREIGN KEY (accttypeid) REFERENCES accounts(accttypeid)," +
            " balance NUMERIC(10,2));";


    private static final String SQL_SELECT_BY_CUSTID = "SELECT * FROM accounts " +
            " WHERE custid = ?;";
    private static final String SQL_SELECT_ALL_ACCOUNTS = "SELECT * FROM accounts; ";

    private static final String SQL_INSERT = "INSERT INTO accounts" +
            " (custid, accttypeid, balance) VALUES " +
            " (?, ?, ?);";
    private static final String SQL_UPDATE = "UPDATE accounts SET " +
            " balance = ? " +
            " WHERE custid = ? " +
            " AND accttypeid = ?;";
    private static final String SQL_DELETE = "DELETE FROM accounts " +
            " WHERE custid = ? " +
            " AND accttypeid = ?;";

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
            PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_CUSTID);
            ps.setString(1, customer.getCustid().toString());
            LOGGER.info("H2: "+ps.toString());
            ResultSet rs = ps.executeQuery();

            AccountTypeDAO accountTypeDAO = new AccountTypeDAO();
            while (rs.next()) {
                accounts.add(new Account(rs.getInt("acctID"),customer, accountTypeDAO.getAccountType(rs.getInt("accttypeid")),rs.getBigDecimal("balance")));
            }
            rs.close();
        }
        catch (SQLException e) {
            DBUtils.printSQLException(e);
        }
        return accounts;
    }

    public void insertAccount(Customer customer, AccountType accountType) {
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT);
            ps.setString(1, customer.getCustid().toString());
            ps.setString(2, accountType.getAccttypeid().toString());
            ps.setBigDecimal(3,new BigDecimal(0));
            LOGGER.info("H2: "+ps.toString());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            DBUtils.printSQLException(e);
        }
    }

    public void updateAccount(Customer customer, Account account, BigDecimal balance) {
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SQL_UPDATE);
            ps.setBigDecimal(1,new BigDecimal(0));
            ps.setString(2, customer.getCustid().toString());
            ps.setString(3, account.getAcctID().toString());
            LOGGER.info("H2: "+ps.toString());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            DBUtils.printSQLException(e);
        }
    }

    public void deleteAccount(Customer customer,Account account) {
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SQL_DELETE);
            ps.setString(1, customer.getCustid().toString());
            ps.setString(2, account.getAcctID().toString());
            LOGGER.info("H2: "+ps.toString());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            DBUtils.printSQLException(e);
        }
    }

}
