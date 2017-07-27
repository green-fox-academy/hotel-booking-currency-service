package com.hiddenite.model.monthlyFee;

import java.util.HashMap;

public class MonthlyFeeData {
  private String type;
  private HashMap<String, Double> attributes;

  public MonthlyFeeData() {
    this.type = "fee";
    this.attributes = new HashMap<>();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public HashMap<String, Double> getAttributes() {
    return attributes;
  }

  public void setAttributes(HashMap<String, Double> attributes) {
    this.attributes = attributes;
  }
}
