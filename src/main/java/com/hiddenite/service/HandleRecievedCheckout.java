package com.hiddenite.service;

import com.hiddenite.model.checkout.Checkout;
import com.hiddenite.model.checkout.CheckoutLinks;
import com.hiddenite.repository.CheckOutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HandleRecievedCheckout {
  @Autowired
  CheckOutRepository checkOutRepository;

  public Checkout response(Checkout checkout) {
    checkOutRepository.save(checkout);
    checkout.setLinks(new CheckoutLinks(checkout.getId()));
    checkOutRepository.save(checkout);
    return checkout;
  }
}