package com.hiddenite.model.checkout;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hiddenite.model.ChargeRequest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class CheckoutAttribute {
  @Id
  @JsonIgnore
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  @JsonProperty(value = "user_id")
  private Long userId;
  @NotNull
  @JsonProperty(value = "booking_id")
  private Long bookingId;
  @NotNull
  private Integer amount;
  @NotNull
  private ChargeRequest.Currency currency;
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

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public ChargeRequest.Currency getCurrency() {
    return currency;
  }

  public void setCurrency(ChargeRequest.Currency currency) {
    this.currency = currency;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
