package com.hiddenite.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.springframework.web.bind.annotation.RestController;

@Entity
public class Transaction {
  private Long hotelID;
  private Long checkoutID;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long transactionID;
  private String currency;
  private int amount;
  private Timestamp created_at;

  public Transaction(Long checkoutID, String currency, int amount) {
    this.hotelID = 1L;
    this.checkoutID = checkoutID;
    this.currency = currency;
    this.amount = amount;
    this.created_at = Timestamp.valueOf(LocalDateTime.now());
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

  public Timestamp getCreated_at() {
    return created_at;
  }

  public void setCreated_at(Timestamp created_at) {
    this.created_at = created_at;
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
}
