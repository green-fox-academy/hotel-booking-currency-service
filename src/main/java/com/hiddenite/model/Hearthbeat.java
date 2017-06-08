package com.hiddenite.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Hearthbeat {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  boolean status;


  public Hearthbeat() {
  }

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }
}
