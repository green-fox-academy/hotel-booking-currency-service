package com.hiddenite.model.checkout;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

@Table(name="Checkout")
@SecondaryTable(name="Checkout_CHECKOUTLINKS",
    pkJoinColumns=@PrimaryKeyJoinColumn(name="Checkout_ID"))
@Entity
public class Checkout {

  @Id
  @JsonIgnore
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne
  @JsonProperty(value = "data")
  private CheckoutData checkoutData;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name="self", column=@Column(table="Checkout_CHECKOUTLINKS"))
  })
  private CheckoutLinks links;

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
