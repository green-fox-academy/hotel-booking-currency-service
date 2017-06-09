package com.hiddenite.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Hearthbeat {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  long id;
  boolean status;
  long id2;
  long id7;


  public Hearthbeat() {
  }

  public Hearthbeat(boolean status) {
    this.status = status;
  }

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }
}
