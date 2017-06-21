package com.hiddenite.model.checkout;


public class CheckoutData{

    private String type;
    private CheckoutAttribute attributes;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public CheckoutAttribute getAttributes() {
    return attributes;
  }

  public void setAttributes(CheckoutAttribute attributes) {
    this.attributes = attributes;
  }
}
