package com.hiddenite.model.checkout;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.Valid;

@Table(name = "Checkout")
@SecondaryTable(name = "Checkout_CHECKOUTLINKS",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "Checkout_ID"))
@Entity
public class Checkout {

  @Id
  @JsonIgnore
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne(cascade = {CascadeType.ALL})
  @JsonProperty(value = "data")
  @Valid
  private CheckoutData checkoutData;

  @Valid
  @Embedded
  @AttributeOverrides({
          @AttributeOverride(name = "self", column = @Column(table = "Checkout_CHECKOUTLINKS"))})
  private CheckoutLinks links;

  public Checkout(CheckoutData checkoutData) {
    this.checkoutData = checkoutData;
  }

  public Checkout() {
  }

  public CheckoutData getCheckoutData() {
    return checkoutData;
  }

  public void setCheckoutData(CheckoutData checkoutData) {
    this.checkoutData = checkoutData;
  }

  public CheckoutLinks getLinks() {
    return links;
  }

  public void setLinks(CheckoutLinks links) {
    this.links = links;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
