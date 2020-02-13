package uk.ac.bath.cm50286.group2.newbank.server.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.bath.cm50286.group2.newbank.server.dao.AccountDAO;
import uk.ac.bath.cm50286.group2.newbank.server.dao.AccountTypeDAO;

import java.math.BigDecimal;

public class Account {

	private static final Logger LOGGER = LogManager.getLogger(Account.class);

	private int custid;
	private String accountName;
	private BigDecimal balance;

	private Integer acctID;
	private Integer acctTypeID;
	private AccountTypeDAO accountTypeDAO = new AccountTypeDAO();


	public Account(int acctID, int custid, int acctTypeID, BigDecimal balance) {
		this.acctID = acctID;
		this.custid = custid;
		this.acctTypeID = acctTypeID;
		this.balance = balance;
	}

	public Integer getAcctID() {
		return acctID;
	}

	public int getCustomerID() {
		return custid;
	}

	public String getAccountName() {
		return accountName;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String toString() {
		return (appendSpace(""+acctID) + " | " + appendSpace(""+custid )+ " | " +
				appendSpace(getAcctType(acctTypeID)) + " | " + appendSpace(""+balance) + " \n");
	}

	public String getAcctType(int acctTypeID){
		return accountTypeDAO.getAccountDesc(acctTypeID);
	}

	public String appendSpace(String s) {
		int spaces = 10 - s.length();
		StringBuilder sb = new StringBuilder(s);
		for (int i = 0; i < spaces; i++) {
			sb.append(" ");
		}
		return sb.toString();
	}




}
