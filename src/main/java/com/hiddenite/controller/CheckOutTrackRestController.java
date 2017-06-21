package com.hiddenite.controller;

import com.hiddenite.model.checkout.Checkout;
import com.hiddenite.model.checkout.CheckoutLinks;
import com.hiddenite.repository.CheckOutRepository;
import com.hiddenite.service.HandleRecievedCheckout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckOutTrackRestController {

  @Autowired
  CheckOutRepository checkOutRepository;

  @Autowired
  HandleRecievedCheckout handleRecievedCheckout;

  @RequestMapping(value = "/api/checkouts")
  public Checkout responseToCheckout(@RequestBody Checkout recievedCheckout) {
    checkOutRepository.save(recievedCheckout);
   recievedCheckout.setLinks(new CheckoutLinks(recievedCheckout.getId()));
    return recievedCheckout;
  }

}
