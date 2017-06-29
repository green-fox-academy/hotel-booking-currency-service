package com.hiddenite.controller;

import com.hiddenite.model.Checkouts;
import com.hiddenite.model.checkout.Checkout;
import com.hiddenite.model.error.ErrorMessage;
import com.hiddenite.model.error.NoIndexException;
import com.hiddenite.service.CheckoutDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

@RestController
public class CheckoutsRestController {
  @Autowired
  private CheckoutDataService checkoutDataService;

  @GetMapping(value = "/api/checkouts")
  public Checkouts getCheckouts(@RequestParam(name = "page", required = false) Integer actualPageNumber, javax.servlet.http.HttpServletRequest request) {
    Checkouts checkouts = new Checkouts();
    if (actualPageNumber != null) {
      checkoutDataService.listCheckoutsByPages(checkouts, actualPageNumber);
    } else {
      checkoutDataService.listCheckoutsByPages(checkouts, 1);
    }
    return checkouts;
  }

  @GetMapping(value = "/checkouts")
  public Checkouts filterMultiCheckouts(HttpServletRequest request) {
    Checkouts checkouts = new Checkouts();
    checkoutDataService.setCheckoutFiltering(checkouts, request);
    return checkouts;
  }

  @GetMapping(value = "/api/checkouts/{id}")
  public Object filterCheckouts(@PathVariable(name = "id") Long id, javax.servlet.http.HttpServletRequest request) throws NoIndexException {
    return checkoutDataService.getCheckoutById(id);
  }

  @DeleteMapping(value = "/api/checkouts/{id}")
  public Object deleteCheckout(@PathVariable(name = "id") Long id, javax.servlet.http.HttpServletRequest request) throws NoIndexException {
    return checkoutDataService.deleteCheckoutById(id);
  }

  @PatchMapping(value = "/api/checkouts/{id}")
  public Object updateCheckout(@RequestBody Checkout checkout, @PathVariable(name = "id") Long id, javax.servlet.http.HttpServletRequest request) throws NoIndexException, InvocationTargetException, IllegalAccessException {
    return checkoutDataService.updateCheckout(checkout);
  }

  @ExceptionHandler(NoIndexException.class)
  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  public ErrorMessage notExistingId(javax.servlet.http.HttpServletRequest request) {
    return new ErrorMessage(404, "Not found", "No checkouts found by id: " + NoIndexException.index);
  }
}
