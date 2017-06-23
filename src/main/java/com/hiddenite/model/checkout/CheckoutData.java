package com.hiddenite.model.checkout;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Entity
public class CheckoutData {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  private String type;

  @Valid
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
