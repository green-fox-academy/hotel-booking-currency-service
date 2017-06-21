package com.hiddenite.controller;

import com.hiddenite.model.checkout.Checkout;
import com.hiddenite.model.checkout.CheckoutLinks;
import com.hiddenite.repository.CheckOutRepository;
import com.hiddenite.service.HandleRecievedCheckout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class CheckOutTrackRestController {

  @Autowired
  CheckOutRepository checkOutRepository;

  @Autowired
  HandleRecievedCheckout handleRecievedCheckout;

  @RequestMapping(value = "/api/checkouts",  method = RequestMethod.POST)
  @ResponseStatus(code = HttpStatus.CREATED)
  public Checkout responseToCheckout(@RequestBody Checkout recievedCheckout) {
    checkOutRepository.save(recievedCheckout);
    recievedCheckout.setLinks(new CheckoutLinks(recievedCheckout.getId()));
    checkOutRepository.save(recievedCheckout);
    return recievedCheckout;
  }

}
