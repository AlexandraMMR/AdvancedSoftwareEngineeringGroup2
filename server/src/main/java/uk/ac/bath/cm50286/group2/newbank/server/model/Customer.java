package uk.ac.bath.cm50286.group2.newbank.server.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Customer {

	private static final Logger LOGGER = LogManager.getLogger(Customer.class);
	private int custid;
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private String email;
	private String address;
	private String postcode;
	private String ninumber;

	public Customer(int custid, String firstname, String lastname, String username,String password,
									String email, String address, String postcode, String ninumber) {
		this.custid=custid;
		this.firstname = firstname;
		this.lastname=lastname;
		this.username = username;
		this.password = password;
		this.email=email;
		this.address=address;
		this.postcode=postcode;
		this.ninumber=ninumber;

	}


	public int getCustid() {
		return custid;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstname;
	}

/*
	public void setName(String name) {
		this.name = name;
	}
*/

	@Override
	public String toString() {
		return (username + ": " + username);
	}
}