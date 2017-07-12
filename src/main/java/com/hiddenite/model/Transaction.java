package com.hiddenite.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.OneToOne;

@Entity
public class Transaction {
  private Long hotelID;
  private Long checkoutID;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long transactionID;
  private String currency;
  private int amount;
  private Timestamp createdAt;

  @OneToOne(cascade = {CascadeType.ALL})
  private ExchangeRate exchangeRate;

  public Transaction(Long checkoutID, String currency, int amount, ExchangeRate exchangeRate) {
    this.hotelID = 1L;
    this.checkoutID = checkoutID;
    this.currency = currency;
    this.amount = amount;
    this.createdAt = Timestamp.valueOf(LocalDateTime.now());
    this.exchangeRate = exchangeRate;
  }

  public Transaction() {
  }

  public Long getTransactionID() {
    return transactionID;
  }

  public void setTransactionID(Long transactionID) {
    this.transactionID = transactionID;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  public Long getHotelID() {
    return hotelID;
  }

  public void setHotelID(Long hotelID) {
    this.hotelID = hotelID;
  }

  public Long getCheckoutID() {
    return checkoutID;
  }

  public void setCheckoutID(Long checkoutID) {
    this.checkoutID = checkoutID;
  }

  public ExchangeRate getExchangeRate() {
    return exchangeRate;
  }

  public void setExchangeRate(ExchangeRate exchangeRate) {
    this.exchangeRate = exchangeRate;
  }
}
