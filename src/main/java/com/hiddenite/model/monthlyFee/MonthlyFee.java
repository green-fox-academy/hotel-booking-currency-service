package com.hiddenite.model.monthlyFee;

import java.util.HashMap;

public class MonthlyFee {
  private HashMap<String, String> links;
  private MonthlyFeeData data;


  public HashMap<String, String> getLinks() {
    return links;
  }

  public void setLinks(HashMap<String, String> links) {
    this.links = links;
  }

  public MonthlyFeeData getData() {
    return data;
  }

  public void setData(MonthlyFeeData data) {
    this.data = data;
  }
}
