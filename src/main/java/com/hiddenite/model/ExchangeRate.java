package com.hiddenite.model;

import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ExchangeRate {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String base;
  private Date date;


  private HashMap<String, Double> rates;

  public String getBase() {
    return base;
  }

  public void setBase(String base) {
    this.base = base;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public HashMap<String, Double> getRates() {
    return rates;
  }

  public void setRates(HashMap<String, Double> rates) {
    this.rates = rates;
  }
}
