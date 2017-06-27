package com.hiddenite.model;

import com.hiddenite.model.checkout.CheckoutData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Checkouts {
  private HashMap<String, String> links;
  private List<CheckoutData> data;

  public Checkouts() {
    data = new ArrayList<>();
    links = new HashMap<>();
  }

  public HashMap<String, String> getLinks() {
    return links;
  }

  public void setLinks(HashMap<String, String> links) {
      this.links = links;
  }

  public List<CheckoutData> getData() {
    return data;
  }

  public void setData(List<CheckoutData> data) {
    this.data = data;
  }

  public void putLinksToMap(String key, String value) {
    links.put(key, value);
  }
}
