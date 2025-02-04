package uk.ac.bath.cm50286.group2.newbank.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.bath.cm50286.group2.newbank.server.controller.AccountController;
import uk.ac.bath.cm50286.group2.newbank.server.controller.AccountTypeController;
import uk.ac.bath.cm50286.group2.newbank.server.controller.CustomerController;
import uk.ac.bath.cm50286.group2.newbank.server.controller.TransactionController;
import uk.ac.bath.cm50286.group2.newbank.server.dao.AccountDAO;
import uk.ac.bath.cm50286.group2.newbank.server.dao.AccountTypeDAO;
import uk.ac.bath.cm50286.group2.newbank.server.dao.CustomerDAO;
import uk.ac.bath.cm50286.group2.newbank.server.model.Customer;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;
import static uk.ac.bath.cm50286.group2.newbank.server.util.StringUtils.requireNonBlank;

public class NewBank {

	private static final Logger LOGGER = LogManager.getLogger(NewBank.class);
	private static final NewBank bank = new NewBank();

	// DAOs
	private static final AccountDAO accountDao = new AccountDAO();
	private static final AccountTypeDAO accountTypeDao = new AccountTypeDAO();
	private static final CustomerDAO customerDao = new CustomerDAO();

	// TODO: add new DAOs here

	// Controllers
	private static final AccountController accountController = new AccountController(accountDao, accountTypeDao);
	private static final AccountTypeController accountTypeController = new AccountTypeController(accountTypeDao);
	private static final CustomerController customerController = new CustomerController(customerDao,accountDao);
	private static final TransactionController transactionController = new TransactionController();

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
	public synchronized String processRequest(final Customer customer, final String request) {
		requireNonNull(customer, "FAIL - Customer cannot be null");
		requireNonBlank(request, "FAIL - Request cannot be null or blank");
		final String[] requestParams = request.split("\\s+");
		System.out.println("Request has " + requestParams.length + " params.");
		for (int i = 0; i < requestParams.length; i++) {
			System.out.println(i + "=" + requestParams[i]);
		}
		if (requestParams[0].equals("SHOWMYACCOUNTS")) {
			return "AccountID  | CustID     | AcctType   | Balance   \n" +
					"-------------------------------------------------\n" +
					accountController.getAccountsAsString(customer);

		}
		if (requestParams[0].equals("SHOWALLACCOUNTTYPES")) {
			return accountTypeController.getAllAccountTypes(customer);

		} else if (requestParams[0].equals("CREATECUSTOMER") && requestParams.length == 9) {
			return customerController.createCustomer(
					customer, requestParams[1], requestParams[2], requestParams[3], requestParams[4], requestParams[5]
					, requestParams[6], requestParams[7], requestParams[8]);
		} else if (requestParams[0].equals("CREATECUSTOMER")) {
			return "Invalid use of command\n FORMAT: First Name,Last Name, Username, Password, Email, Address, Postcode, NI number";
		} else if (requestParams[0].equals("SHOWALLACCOUNTS")) {
			return
					"AccountID  | CustID     | AcctType   | Balance   \n" +
							"-------------------------------------------------\n" +
							accountController.getAllAccounts(customer);
		} else if (requestParams[0].equals("SHOWCUSTOMERS")) {
			return
					"CUSTID     | Firstname  | Lastname   | Username  \n" +
							"-------------------------------------------------\n" +
							customerController.getOtherCustomers(customer);
		} else if (requestParams[0].equals("TRANSFER") && requestParams.length == 5) {
			String s = accountController.transfer(customer, requestParams[1], Integer.parseInt(requestParams[2]),
					Integer.parseInt(requestParams[3]), new BigDecimal(requestParams[4]));
			transactionController.createTransaction(customer, Integer.parseInt(requestParams[1]), Integer.parseInt(requestParams[2]),
					Integer.parseInt(requestParams[3]), new BigDecimal(requestParams[4]));
			return s;
		} else if (requestParams[0].equals("DEPOSIT") && requestParams.length == 3) {
			return accountController.deposit(customer, Integer.parseInt(requestParams[1]), new BigDecimal(requestParams[2]));
		} else if (requestParams[0].equals("SHOWTRANSACTIONS") && requestParams.length == 1) {
			return "TRANSID    | TRANSTYPE  | ACCTFROM   | ACCTTO     | AMOUNT    \n" +
					"--------------------------------------------------------------\n" +
					transactionController.getTransactions(customer);
		} else if (requestParams[0].equals("PAY") && requestParams.length == 4) {
			return accountController.payCustomer(customer, Integer.parseInt(requestParams[1]), Integer.parseInt(requestParams[2]),
					new BigDecimal(requestParams[3]));
		} else if (requestParams[0].equals("HELP") && requestParams.length == 1) {
			return
					"Available Commands:\n" +
							"SHOWMYACCOUNTS\n" +
							"SHOWCUSTOMERS\n" +
							"PAY <ACCTID FROM> <ACCTID TO> <AMOUNT>";
		} else if (requestParams[0].equals("HELPADMIN") && requestParams.length == 1) {
			return
					"Available Commands:" +
							"DEPOSIT <ACCTID> <AMOUNT>\n" +
							"SHOWALLACCOUNTS\n" +
							"CREATECUSTOMER <FirstName> <LastName> <Username> <Password> <Email> <Address> <Postcode> <NInumber>";
		} else if (requestParams.length == 2 && "NEWACCOUNT".equals(requestParams[0])) {
			try {
				return accountController.createAccount(customer, requestParams[1]);
			} catch (NullPointerException | IllegalArgumentException e) {
				return e.getMessage();
			}
		} else return "FAIL";


	}


}
