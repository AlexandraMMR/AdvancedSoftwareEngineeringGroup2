package uk.ac.bath.cm50286.group2.newbank.server.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Transaction {

  private static final Logger LOGGER = LogManager.getLogger(Transaction.class);
  private Integer transactionid;
  private Integer transtypeid;
  private Integer transfrom;
  private Integer transto;
  private Integer amount;

  public Transaction(int transactionid, int transtypeid, int transfrom, int transto, int amount){
    this.transactionid=transactionid;
    this.transtypeid = transtypeid;
    this.transfrom=transfrom;
    this.transto = transto;
    this.amount = amount;
  }

  public Integer getAmount() {
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

}
