package com.hiddenite.service;

import com.google.gson.Gson;
import com.hiddenite.model.Treshold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeeService {
  private Gson gson;

  @Autowired
  public FeeService(Gson gson) {
    this.gson = gson;
  }

  public Double getTresholdValue(Double amount) {
    Treshold treshold = gson.fromJson(System.getenv("FEE_TRESHOLD"), Treshold.class);
    Double feePercentage = 0.05;
    if (treshold != null) {
      if (amount > treshold.getTresholds().get(0).get("min-amount") && amount < treshold.getTresholds().get(1).get("max-amount")) {
        feePercentage = Double.valueOf(treshold.getTresholds().get(0).get("min-amount")) / 100;
      }
      if (amount > treshold.getTresholds().get(1).get("max-amount")) {
        feePercentage = Double.valueOf(treshold.getTresholds().get(1).get("max-amount")) / 100;
      }
    }
    return feePercentage;
  }

}
