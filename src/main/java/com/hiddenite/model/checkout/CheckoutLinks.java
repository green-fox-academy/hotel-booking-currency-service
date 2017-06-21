package com.hiddenite.model.checkout;

import javax.persistence.Embeddable;

@Embeddable
public class CheckoutLinks {

  private String self;

  public CheckoutLinks(Long id) {
    this.self = "https://your-hostname.com/checkout/" + id;
  }

  public CheckoutLinks() {
  }

  public String getSelf() {
    return self;
  }

  public void setSelf(Long id) {
    this.self = "https://your-hostname.com/checkout/" + id;
  }
}
