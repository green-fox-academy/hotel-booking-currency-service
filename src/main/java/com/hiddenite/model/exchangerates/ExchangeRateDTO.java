package com.hiddenite.model.exchangerates;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class ExchangeRateDTO {
  @EmbeddedId
  ExchangeRateKey exchangeRateKey;
  double rate;

  public ExchangeRateDTO(ExchangeRateKey exchangeRateKey, double rate) {
    this.exchangeRateKey = exchangeRateKey;
    this.rate = rate;
  }

  public ExchangeRateDTO() {
  }

  public double getRate() {
    return rate;
  }

  public void setRate(double rate) {
    this.rate = rate;
  }

  public ExchangeRateKey getExchangeRateKey() {
    return exchangeRateKey;
  }

  public void setExchangeRateKey(ExchangeRateKey exchangeRateKey) {
    this.exchangeRateKey = exchangeRateKey;
  }

}
