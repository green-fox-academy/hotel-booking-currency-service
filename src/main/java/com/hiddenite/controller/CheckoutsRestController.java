package com.hiddenite.controller;

import com.hiddenite.model.Checkouts;
import com.hiddenite.model.checkout.Checkout;
import com.hiddenite.repository.CheckoutDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckoutsRestController {

  @Autowired
  CheckoutDataRepository checkoutDataRepository;

  @GetMapping(value = "/checkouts")
  public Checkouts getCheckouts() {
    Checkouts checkouts = new Checkouts();
    checkouts.setData(checkoutDataRepository.findAll());
    checkouts.setSelf("https://hotel-booking-payment.herokuapp.com/checkouts");
    return checkouts;
  }

}
