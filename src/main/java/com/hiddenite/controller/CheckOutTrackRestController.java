package com.hiddenite.controller;

import com.hiddenite.model.error.ErrorMessage;
import com.hiddenite.model.checkout.Checkout;
import com.hiddenite.service.ErrorMessageHandler;
import com.hiddenite.service.HandleRecievedCheckout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CheckOutTrackRestController {

  @Autowired
  private ErrorMessageHandler errorMessageHandler;

  @Autowired
  private HandleRecievedCheckout handleRecievedCheckout;

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  public ErrorMessage missingBodyParamter(MethodArgumentNotValidException e, javax.servlet.http.HttpServletRequest request) {
    return errorMessageHandler.getErrorMessageWithMissingFields(e);
  }

  @PostMapping(value = "/api/checkouts")
  @ResponseStatus(code = HttpStatus.CREATED)
  public Checkout responseToCheckout(@RequestBody @Valid Checkout recievedCheckout, javax.servlet.http.HttpServletRequest request) {
    return handleRecievedCheckout.response(recievedCheckout);
  }
}
