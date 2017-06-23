package com.hiddenite.controller;

import com.hiddenite.model.Checkouts;
import com.hiddenite.repository.CheckoutDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckoutsRestController {

  @Autowired
  CheckoutDataRepository checkoutDataRepository;

  @GetMapping(value = "/checkouts")
  public Checkouts getCheckouts() {
    Checkouts checkouts = new Checkouts(checkoutDataRepository);
    checkouts.setData(checkoutDataRepository.findAll(new PageRequest(1,5, Sort.Direction.DESC, "id")));
    checkouts.setLinks();
    return checkouts;
  }
}
