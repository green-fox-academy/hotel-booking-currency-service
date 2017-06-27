package com.hiddenite.controller;

import com.hiddenite.model.Checkouts;
import com.hiddenite.model.error.ErrorMessage;
import com.hiddenite.model.error.NoIndexException;
import com.hiddenite.service.CheckoutDataService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class CheckoutsRestController {
  @Autowired
  private CheckoutDataService checkoutDataService;

  @GetMapping(value = "/api/checkouts")
  public Checkouts getCheckouts(@RequestParam(name = "page", required = false) Integer actualPageNumber) {
    Checkouts checkouts = new Checkouts();
    if (actualPageNumber != null) {
      checkoutDataService.listCheckoutsByPages(checkouts, actualPageNumber);
    } else {
      checkoutDataService.listCheckoutsByPages(checkouts, 1);
    }
    return checkouts;
  }

  @GetMapping(value = "/checkouts")
  public Checkouts filterCheckouts(HttpServletRequest request) {
    Checkouts checkouts = new Checkouts();
    checkoutDataService.setCheckoutFiltering(checkouts, request);
    return checkouts;
  }
  
  @GetMapping(value = "/api/checkouts/{id}")
  public Object filterCheckouts(@PathVariable(name = "id") Long id) throws NoIndexException {
    return checkoutDataService.getCheckoutById(id);
  }

  @DeleteMapping(value = "/api/checkouts/{id}")
  public Object deleteCheckout(@PathVariable(name = "id") Long id) throws NoIndexException {
    return checkoutDataService.deleteCheckoutById(id);
  }

  @ExceptionHandler(NoIndexException.class)
  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  public ErrorMessage notExistingId() {
    return new ErrorMessage(404, "Not found", "No checkouts found by id: " + NoIndexException.index);
  }

}
