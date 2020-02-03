import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Account {

	private static final Logger LOGGER = LogManager.getLogger(Account.class);
	private String accountName;
	private double openingBalance;

	public Account(String accountName, double openingBalance) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
	}
	
	public String toString() {
		return (accountName + ": " + openingBalance);
	}

}
