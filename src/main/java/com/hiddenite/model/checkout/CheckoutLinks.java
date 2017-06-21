package com.hiddenite.model.checkout;

public class CheckoutLinks {

  private String self;

  public String getSelf() {
    return self;
  }

  public void setSelf(Long id) {
    this.self = "https://your-hostname.com/checkout/" + id;
  }
}
