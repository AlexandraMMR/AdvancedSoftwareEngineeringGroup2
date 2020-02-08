package uk.ac.bath.cm50286.group2.newbank.server.model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AccountType {

    private static final Logger LOGGER = LogManager.getLogger(AccountType.class);
    private int accttypeid;
    private String acctdesc;

    public AccountType(int accttypeid,String acctdesc) {
        this.accttypeid = accttypeid;
        this.acctdesc = acctdesc;
    }

    public Integer getAccttypeid() {
        return accttypeid;
    }

    public String getAcctdesc() {
        return acctdesc;
    }



}
