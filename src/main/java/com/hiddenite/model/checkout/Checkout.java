package com.hiddenite.model.checkout;



public class Checkout {

  private CheckoutData data;
  private CheckoutLinks links;

  public CheckoutData getData() {
    return data;
  }

  public void setData(CheckoutData data) {
    this.data = data;
  }

  public CheckoutLinks getLinks() {
    return links;
  }

  public void setLinks(CheckoutLinks links) {
    this.links = links;
  }
}
