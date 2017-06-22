package com.hiddenite.model;

import com.hiddenite.model.checkout.CheckoutData;
import java.util.List;

public class Checkouts {

  private List<CheckoutData> Data;
  private String self;
  private String prev;
  private String next;
  private String last;

  public Checkouts() {
  }

  public String getSelf() {
    return self;
  }

  public void setSelf(String self) {
    this.self = self;
  }

  public String getPrev() {
    return prev;
  }

  public void setPrev(String prev) {
    this.prev = prev;
  }

  public String getNext() {
    return next;
  }

  public void setNext(String next) {
    this.next = next;
  }

  public String getLast() {
    return last;
  }

  public void setLast(String last) {
    this.last = last;
  }

  public List<CheckoutData> getData() {
    return Data;
  }

  public void setData(List<CheckoutData> data) {
    Data = data;
  }
}
