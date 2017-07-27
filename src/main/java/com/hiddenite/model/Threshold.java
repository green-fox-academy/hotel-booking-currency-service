package com.hiddenite.model;

import java.util.HashMap;
import java.util.List;

public class Threshold {
  private List<HashMap<String, Integer>> tresholds;

  public Threshold() {
  }

  public Threshold(List<HashMap<String, Integer>> tresholds) {
    this.tresholds = tresholds;
  }

  public List<HashMap<String, Integer>> getTresholds() {
    return tresholds;
  }

  public void setTresholds(List<HashMap<String, Integer>> tresholds) {
    this.tresholds = tresholds;
  }
}
