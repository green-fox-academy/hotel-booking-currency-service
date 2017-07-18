package com.hiddenite.model;

import org.springframework.stereotype.Component;

@Component
public class HotelBalance {
  private HotelBalanceData data;
  private String links;

  public HotelBalanceData getData() {
    return data;
  }

  public void setData(HotelBalanceData data) {
    this.data = data;
  }

  public String getLinks() {
    return links;
  }

  public void setLinks(String links) {
    this.links = links;
  }
}
