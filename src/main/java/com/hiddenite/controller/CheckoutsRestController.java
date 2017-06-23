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
  public Checkouts getCheckouts(@RequestParam(name = "page", required = false) Integer actualPageNumber) {
    Checkouts checkouts = new Checkouts();
    if (actualPageNumber != null) {
      checkoutDataPaginatorService.setData(checkouts, actualPageNumber);
      checkoutDataPaginatorService.setLinks(checkouts, actualPageNumber);
    } else {
      checkoutDataPaginatorService.setData(checkouts, 0);
      checkoutDataPaginatorService.setLinks(checkouts, 0);
    }
    return checkouts;
  }
}
