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

    public static Integer getAcctTypeID(Integer accttypeid) {
    return accttypeid;
    }

    public Integer getAccttypeid() {
        return accttypeid;
    }

    public String getAcctdesc() {
        return acctdesc;
    }

    @Override
    public String toString() {
        return appendSpace(""+accttypeid) + " | " + appendSpace(acctdesc) +"\n";
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
