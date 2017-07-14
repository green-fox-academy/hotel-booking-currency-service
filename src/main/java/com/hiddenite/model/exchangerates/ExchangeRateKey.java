package com.hiddenite.model.exchangerates;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ExchangeRateKey implements Serializable{
  private String date;
  private String base;
  private String foreignCurrency;

  public ExchangeRateKey(String date, String base, String foreignCurrency) {
    this.date = date;
    this.base = base;
    this.foreignCurrency = foreignCurrency;
  }

  public ExchangeRateKey() {
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
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
