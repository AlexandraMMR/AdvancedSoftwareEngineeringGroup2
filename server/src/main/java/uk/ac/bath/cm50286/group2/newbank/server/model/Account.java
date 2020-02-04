package uk.ac.bath.cm50286.group2.newbank.server.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class Account {

	private static final Logger LOGGER = LogManager.getLogger(Account.class);

	private Customer customer;
	private String accountName;
	private BigDecimal balance;

	public Account(Customer customer,String accountName, BigDecimal balance) {
		this.customer = customer;
		this.accountName = accountName;
		this.balance = balance;
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
		return (accountName + ": " + balance);
	}

}
