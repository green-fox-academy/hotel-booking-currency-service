package com.hiddenite.controller;

import com.hiddenite.model.Checkouts;
import com.hiddenite.service.CheckoutDataPaginatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckoutsRestController {

  @Autowired
  CheckoutDataPaginatorService checkoutDataPaginatorService;

  @GetMapping(value = "/checkouts")
  public Checkouts getCheckouts(@RequestParam(name = "page", required = false) int actualPageNumber) {
    Checkouts checkouts = new Checkouts();
    checkoutDataPaginatorService.setData(checkouts, actualPageNumber);
    checkoutDataPaginatorService.setLinks(checkouts,actualPageNumber);
    return checkouts;
  }
}
