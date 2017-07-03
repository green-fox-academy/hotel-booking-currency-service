package com.hiddenite.model;

import java.util.HashMap;

public class HotelBalance {
  private String links;
  private String type;
  private HashMap<String, Integer> attributes;

  public HotelBalance() {
  }

  public HotelBalance(String links,
      HashMap<String, Integer> attributes) {
    this.links = links;
    this.type = "balances";
    this.attributes = attributes;
  }

  public String getLinks() {
    return links;
  }

  public void setLinks(String links) {
    this.links = links;
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
