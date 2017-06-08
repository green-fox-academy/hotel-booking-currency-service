package com.hiddenite.model;

import org.springframework.stereotype.Component;

@Component
public class Status {
  String status;


  public Status() {
    status = "ok";
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
