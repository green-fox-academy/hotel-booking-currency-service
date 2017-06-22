package com.hiddenite.model.checkout;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;


@Entity
public class CheckoutData {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String type;

  @OneToOne(cascade = {CascadeType.ALL})
  private CheckoutAttribute attributes;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public CheckoutAttribute getAttributes() {
    return attributes;
  }

  public void setAttributes(CheckoutAttribute attributes) {
    this.attributes = attributes;
  }
}
