package com.hiddenite.model;

import java.util.HashMap;
import java.util.List;

public class Treshold {
  private List<HashMap<String, Integer>> tresholds;

  public Treshold() {
  }

  public Treshold(List<HashMap<String, Integer>> tresholds) {
    this.tresholds = tresholds;
  }

  public List<HashMap<String, Integer>> getTresholds() {
    return tresholds;
  }

  public void setTresholds(List<HashMap<String, Integer>> tresholds) {
    this.tresholds = tresholds;
  }
}
