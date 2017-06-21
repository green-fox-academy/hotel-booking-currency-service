package com.hiddenite.model.checkout;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckoutAttribute {

  @JsonProperty(value = "user_id")
  private Long userId;
  @JsonProperty(value = "booking_id")
  private Long bookingId;
  private int amount;
  private String  currency;
  private String status;

  public CheckoutAttribute() {
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getBookingId() {
    return bookingId;
  }

  public void setBookingId(Long bookingId) {
    this.bookingId = bookingId;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
