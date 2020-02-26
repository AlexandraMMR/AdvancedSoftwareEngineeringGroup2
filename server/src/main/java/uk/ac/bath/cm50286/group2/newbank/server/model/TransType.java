package uk.ac.bath.cm50286.group2.newbank.server.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TransType {
  private static final Logger LOGGER = LogManager.getLogger(Transaction.class);
  private Integer transtypeid;
  private String transtypedesc;


  public TransType(int transtypeid, String transtypedesc){
    this.transtypeid=transtypeid;
    this.transtypedesc = transtypedesc;
  }

  public Integer getTranstypeid() {
    return transtypeid;
  }


  public String getTranstypedesc() {
    return transtypedesc;
  }
}
