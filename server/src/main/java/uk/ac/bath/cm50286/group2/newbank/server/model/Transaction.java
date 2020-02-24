package uk.ac.bath.cm50286.group2.newbank.server.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class Transaction {

  private static final Logger LOGGER = LogManager.getLogger(Transaction.class);
  private Integer transactionid;
  private Integer transtypeid;
  private Integer transfrom;
  private Integer transto;
  private BigDecimal amount;

  public Transaction(int transactionid, int transtypeid, int transfrom, int transto, BigDecimal amount){
    this.transactionid=transactionid;
    this.transtypeid = transtypeid;
    this.transfrom=transfrom;
    this.transto = transto;
    this.amount = amount;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public Integer getTransactionid() {
    return transactionid;
  }

  public Integer getTransfrom() {
    return transfrom;
  }

  public Integer getTransto() {
    return transto;
  }

  public Integer getTranstypeid() {
    return transtypeid;
  }

  public String toString() {
    return (appendSpace(""+transactionid) + " | " + appendSpace(""+transtypeid )+ " | " +
        appendSpace(""+transfrom) + " | " + appendSpace(""+transto) + appendSpace(""+amount)+ " \n");
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
