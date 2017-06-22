package com.hiddenite.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hiddenite.model.checkout.CheckoutData;
import com.hiddenite.repository.CheckOutRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

public class Checkouts {
  @JsonIgnore
  @Autowired
  CheckOutRepository checkOutRepository;

  private List<CheckoutData> Data;

  public HashMap<String, String> getLinks() {
    return links;
  }

  public void setLinks(HashMap<String, String> links) {
    this.links = links;
  }

  private HashMap<String, String> links;

  public Checkouts() {
    links = new HashMap<>();
    if (checkOutRepository.count() < 20) {
      links.put("self", "https://your-hostname.com/api/checkout");
    }
  }

  public List<CheckoutData> getData() {
    return Data;
  }

  public void setData(List<CheckoutData> data) {
    Data = data;
  }
}
