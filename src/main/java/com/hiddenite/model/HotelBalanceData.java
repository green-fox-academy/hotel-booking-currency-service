package com.hiddenite.model;

import java.util.HashMap;

public class HotelBalanceData {

  private String type;
  private HashMap<String, Integer> attributes;

  public HotelBalanceData() {
    this.type = "balances";
    attributes = new HashMap<>();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public HashMap<String, Integer> getAttributes() {
    return attributes;
  }

  public void setAttributes(HashMap<String, Integer> attributes) {
    this.attributes = attributes;
  }
}
