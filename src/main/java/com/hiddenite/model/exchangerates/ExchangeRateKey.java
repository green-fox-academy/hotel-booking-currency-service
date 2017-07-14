package com.hiddenite.model.exchangerates;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

@Embeddable
public class ExchangeRateKey implements Serializable{
  private Date date;
  private String base;
  private String foreignCurrency;

  public ExchangeRateKey(Date date, String base, String foreignCurrency) {
    this.date = date;
    this.base = base;
    this.foreignCurrency = foreignCurrency;
  }

  public ExchangeRateKey() {
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getBase() {
    return base;
  }

  public void setBase(String base) {
    this.base = base;
  }

  public String getForeignCurrency() {
    return foreignCurrency;
  }

  public void setForeignCurrency(String foreignCurrency) {
    this.foreignCurrency = foreignCurrency;
  }
}
