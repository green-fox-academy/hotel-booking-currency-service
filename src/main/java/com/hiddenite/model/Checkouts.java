package com.hiddenite.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hiddenite.model.checkout.CheckoutData;
import com.hiddenite.repository.CheckoutDataRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Checkouts {
  @JsonIgnore
  CheckoutDataRepository checkoutDataRepository;
  private HashMap<String, String> links;
  private List<CheckoutData> data;

  public Checkouts(CheckoutDataRepository checkoutDataRepository) {
    this.checkoutDataRepository = checkoutDataRepository;
    data = new ArrayList<>();
    links = new HashMap<>();
  }

  public List<CheckoutData> getData() {
    return data;
  }

  public void setData(List<CheckoutData> dataList) {
    data = dataList;
  }



  public HashMap<String, String> getLinks() {
    return links;
  }

  public void setLinks() {
    if (this.checkoutDataRepository.count() < 20) {
      links.put("self", "https://your-hostname.com/api/checkout");
      links.put("ssecond", "https://your-hostname.com/api/checkout");
    }
  }
}
