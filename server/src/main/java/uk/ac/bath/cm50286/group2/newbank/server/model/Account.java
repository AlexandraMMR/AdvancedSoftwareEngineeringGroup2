package uk.ac.bath.cm50286.group2.newbank.server.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class Account {

	private static final Logger LOGGER = LogManager.getLogger(Account.class);

	private Customer customer;
	private String accountName;
	private BigDecimal balance;

	private Integer acctID;
	private AccountType acctType;

	public Account(int acctID, Customer customer, AccountType acctType, BigDecimal balance) {
		this.acctID = acctID;
		this.customer = customer;
		this.acctType = acctType;
		this.balance = balance;
	}

	public Integer getAcctID() {
		return acctID;
	}

	public Customer getCustomer() {
		return customer;
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
		return (acctID.toString() + " " + acctType.getAcctdesc() + ": " + balance);
	}

	public AccountType getAcctType(){
		return acctType;
	}



}
