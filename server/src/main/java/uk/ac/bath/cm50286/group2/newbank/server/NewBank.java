package uk.ac.bath.cm50286.group2.newbank.server;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import uk.ac.bath.cm50286.group2.newbank.server.controller.AccountController;
import uk.ac.bath.cm50286.group2.newbank.server.controller.CustomerController;
import uk.ac.bath.cm50286.group2.newbank.server.dao.AccountDAO;
import uk.ac.bath.cm50286.group2.newbank.server.dao.CustomerDAO;
import uk.ac.bath.cm50286.group2.newbank.server.model.Customer;

public class NewBank {

	private static final Logger LOGGER = LogManager.getLogger(NewBank.class);
	private static final NewBank bank = new NewBank();

	// DAOs
	private static final AccountDAO accountDao = new AccountDAO();
	private static final CustomerDAO customerDao = new CustomerDAO();
	// TODO: add new DAOs here

	// Controllers
	private static final AccountController accountController = new AccountController(accountDao);
	private static final CustomerController customerController = new CustomerController(customerDao,accountDao);
	// TODO: add new Controllers here

	public static NewBank getBank() {
		return bank;
	}
	
	public synchronized Customer checkLogInDetails(String username, String password) {
		Customer customer = customerDao.getCustomer(username);
		if (customer.getPassword().equals(password)) {
			return customer;
		}
		return null;
	}

	// commands from the NewBank customer are processed in this method
	public synchronized String processRequest(Customer customer, String request) {
		String[] requestParams = request.split("\\s+");
		System.out.println("Request has "+requestParams.length+" params.");
		for (int i=0; i<requestParams.length; i++) {
			System.out.println(i+"="+requestParams[i]);
		}
		switch(requestParams[0]) {
			case "SHOWMYACCOUNTS" : return accountController.getAccountsAsString(customer);
			case "CREATECUSTOMER" :
				if(requestParams.length < 4){
					return "FAIL: An insufficient number of arguments were supplied for the command";
				}
				else {
					return customerController.createCustomer(customer, requestParams[1], requestParams[2], requestParams[3]);
				}
			default : return "FAIL";
		}
	}

}
