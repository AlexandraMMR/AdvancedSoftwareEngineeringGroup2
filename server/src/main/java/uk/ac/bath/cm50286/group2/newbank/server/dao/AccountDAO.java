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

    /*private static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS accounts" +
            "(customer VARCHAR(255) NOT NULL," +
            " name VARCHAR(255) NOT NULL ," +
            " balance NUMERIC(10,2), " +
            " PRIMARY KEY (customer,name)," +
            " FOREIGN KEY (customer) REFERENCES customers(username))";*/
    private static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS account" +
        "(acctid INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
        "custid INT," +
        "accttypeid INT," +
        " FOREIGN KEY (custid) REFERENCES customers(custid)," +
        " FOREIGN KEY (accttypeid) REFERENCES account(accttypeid)," +
        " balance NUMERIC(10,2));";

    private static final String SQL_SELECT_BY_CUSTID = "SELECT * FROM account " +
        " WHERE custid = ?;";
    private static final String SQL_SELECT_ALL_ACCOUNTS = "SELECT * FROM account; ";

    private static final String SQL_INSERT = "INSERT INTO account" +
        " (custid, accttypeid, balance) VALUES " +
        " (?, ?, ?);";
    private static final String SQL_UPDATE = "UPDATE account SET " +
        " balance = ? " +
        " WHERE custid = ? " +
        " AND accttypeid = ?;";
    private static final String SQL_DELETE = "DELETE FROM account " +
        " WHERE custid = ? " +
        " AND accttypeid = ?;";
    private static final String SQL_GET_ACCT_ID = "SELECT acctid from account " +
        "WHERE custid= ?;";
    private static final String SQL_GET_CUST_ID = "SELECT custid from account " +
        "WHERE acctid = ?;";
    private static final String SQL_GET_ACCT_BALANCE = "SELECT balance from account " +
        "WHERE acctid= ?;";
    private static final String SQL_ADD_ACCT_BALANCE = "UPDATE account  " +
        "set balance=balance+? WHERE acctid=?;";
    private static final String SQL_SUBTRACT_ACCT_BALANCE = "UPDATE account  " +
            "set balance=balance-? WHERE acctid=?;";
    private static final String SQL_SELECT_ALL = "SELECT * from account;";
    private static final String SQL_SELECT_ACCOUNT = "SELECT * from account " +
            "WHERE acctid = ?;";

    public void createTable() {
        try (Connection connection = DBUtils.getConnection()) {
            Statement statement = connection.createStatement();
            LOGGER.info("H2: " + SQL_CREATE);
            statement.execute(SQL_CREATE);
        } catch (SQLException e) {
            DBUtils.printSQLException(e);
        }
    }

    public List<Account> getAccountsForCustomer(Customer customer) {
        List<Account> accounts = new ArrayList<>();
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_CUSTID);
            ps.setString(1, customer.getCustid().toString());
            LOGGER.info("H2: " + ps.toString());
            ResultSet rs = ps.executeQuery();

            AccountTypeDAO accountTypeDAO = new AccountTypeDAO();
            while (rs.next()) {
                accounts.add(new Account(
                    rs.getInt("acctID"),
                    customer.getCustid(),
                    rs.getInt("accttypeid"),
                    rs.getBigDecimal("balance")));
            }
            rs.close();
        } catch (SQLException e) {
            DBUtils.printSQLException(e);
        }
        return accounts;
    }

    public List<Integer> getAcctIDforCustomer(Customer customer) {
        List<Integer> accounts = new ArrayList<>();

        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SQL_GET_ACCT_ID);
            ps.setString(1, customer.getCustid().toString());
            LOGGER.info("H2: " + ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                accounts.add(rs.getInt("acctID"));
            }
            rs.close();
        } catch (SQLException e) {
            DBUtils.printSQLException(e);
        }
        return accounts;
    }

    public void insertAccount(Customer customer, int accountTypeID) {
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT);
            ps.setString(1, customer.getCustid().toString());
            ps.setInt(2, accountTypeID);
            ps.setBigDecimal(3, new BigDecimal(0));
            LOGGER.info("H2: " + ps.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            DBUtils.printSQLException(e);
        }
    }

    public void updateAccount(Customer customer, Account account, BigDecimal balance) {
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SQL_UPDATE);
            ps.setBigDecimal(1, new BigDecimal(0));
            ps.setString(2, customer.getCustid().toString());
            ps.setString(3, account.getAcctID().toString());
            LOGGER.info("H2: " + ps.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            DBUtils.printSQLException(e);
        }
    }

    public void deleteAccount(Customer customer, Account account) {
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SQL_DELETE);
            ps.setString(1, customer.getCustid().toString());
            ps.setString(2, account.getAcctID().toString());
            LOGGER.info("H2: " + ps.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            DBUtils.printSQLException(e);
        }
    }

    public Integer getCustIDforAcct(int transfrom) {
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SQL_GET_CUST_ID);
            ps.setInt(1, transfrom);
            LOGGER.info("H2: " + ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            return rs.getInt("custid");
            }

        } catch (SQLException e) {
            DBUtils.printSQLException(e);
        }
        return 0;
    }

    public BigDecimal getAcctBalance(int acctid) {
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SQL_GET_ACCT_BALANCE);
            ps.setInt(1, acctid);
            LOGGER.info("H2: " + ps.toString());
            return ps.executeQuery().getBigDecimal("balance");
        } catch (SQLException e) {
            DBUtils.printSQLException(e);
        }
        return new BigDecimal(0);
    }

    public boolean depositToAccount(int acctid, BigDecimal amount) {
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SQL_ADD_ACCT_BALANCE);
            ps.setBigDecimal(1, amount);
            ps.setInt(2, acctid);

            LOGGER.info("H2: " + ps.toString());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            DBUtils.printSQLException(e);
            return false;
        }
//        return new BigDecimal(0);

    }

    public boolean withdrawfromAccount(int acctid, BigDecimal amount) {
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SQL_SUBTRACT_ACCT_BALANCE);
            ps.setBigDecimal(1, amount);
            ps.setInt(2, acctid);

            LOGGER.info("H2: " + ps.toString());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            DBUtils.printSQLException(e);
            return false;
        }
//        return new BigDecimal(0);

    }


    public List<Account> getAllAccounts() {
        List<Account> allAccounts = new ArrayList<>();
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SQL_SELECT_ALL);
            LOGGER.info("H2: " + ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                allAccounts.add(new Account(
                    rs.getInt("acctid"),
                    rs.getInt("custid"),
                    rs.getInt("accttypeid"),
                    rs.getBigDecimal("balance")));
            }
            rs.close();
        } catch (SQLException e) {
            DBUtils.printSQLException(e);
        }
        return allAccounts;
    }

    public Account getAccount(int accountID) {
        Account account;
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SQL_SELECT_ACCOUNT);
            ps.setInt(1, accountID);
            LOGGER.info("H2: " + ps.toString());
            ResultSet rs = ps.executeQuery();
            account = new Account(rs.getInt("acctid"), rs.getInt("custid"), rs.getInt("accttypeid"), rs.getBigDecimal("balance"));
            rs.close();
            return account;
        } catch (SQLException e) {
            DBUtils.printSQLException(e);
        }
        return null;
    }


}
