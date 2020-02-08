package uk.ac.bath.cm50286.group2.newbank.server.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.bath.cm50286.group2.newbank.server.dao.AccountDAO;
import uk.ac.bath.cm50286.group2.newbank.server.dao.CustomerDAO;
import uk.ac.bath.cm50286.group2.newbank.server.model.Account;
import uk.ac.bath.cm50286.group2.newbank.server.model.Customer;

import java.util.List;

public class CustomerController {

    private static final Logger LOGGER = LogManager.getLogger(CustomerController.class);

    private AccountDAO accountDAO;
    private CustomerDAO customerDAO;

    public CustomerController(CustomerDAO customerDAO,AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
        this.customerDAO = customerDAO;
    }

    public String getOtherCustomers(Customer thisCustomer) {
        List<Customer> customerList = customerDAO.getAllCustomers();
        LOGGER.info("Found "+customerList.size()+" customers.");
        StringBuilder sb = new StringBuilder();
        for (Customer customer : customerList) {
            if (!customer.equals(thisCustomer) && !customer.getUsername().equals("admin")) {
                sb.append(customer.toString());
            }
        }
        return sb.toString();
    }

    public String createCustomer(Customer customer,String firstname, String lastname, String username,String password,
                                 String email, String address, String postcode, String ninumber ) {
        LOGGER.info("Creating customer with username '"+username+"', name '"+firstname+" "+lastname+"'.");
        if (!customer.getUsername().equals("admin")) {
            return "ERROR: Only the admin can create new customers.";
        }
        Customer existing = customerDAO.getCustomer(username);
        if (existing != null) {
            return "ERROR: Customer with username "+username+" already exists.";
        }
        customerDAO.insertCustomer(firstname, lastname, username, password, email, address, postcode, ninumber);
        Customer newCustomer = customerDAO.getCustomer(username);
        accountDAO.insertAccount(newCustomer,"Main");
        return "Customer "+username+" and Main account created.";
    }

    public void updatePassword(Customer customer,String newPassword) {
        customerDAO.updateCustomer(customer.getUsername(),newPassword,customer.getFirstName());
    }

    public void updateName(Customer customer,String newName) {
        customerDAO.updateCustomer(customer.getUsername(),customer.getPassword(),newName);
    }

}
