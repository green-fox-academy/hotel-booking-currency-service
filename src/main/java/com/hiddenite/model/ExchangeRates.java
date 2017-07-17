package com.hiddenite.model;

import javax.persistence.*;
import java.util.Map;

@Entity
public class ExchangeRates {
  private String base;
  @Id
  private String date;
  @ElementCollection
  @JoinTable(name = "CURRENCY_RATES",
          joinColumns = @JoinColumn(name = "date"))
  @Column(name = "RATE")
  private Map<String, Double> rates;

  public String getBase() {
    return base;
  }

  public void setBase(String base) {
    this.base = base;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public Map<String, Double> getRates() {
    return rates;
  }

  public void setRates(Map<String, Double> rates) {
    this.rates = rates;
  }
}
