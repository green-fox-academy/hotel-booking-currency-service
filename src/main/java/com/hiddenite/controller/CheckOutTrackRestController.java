package com.hiddenite.controller;

import com.hiddenite.model.ErrorMessage;
import com.hiddenite.model.checkout.Checkout;
import com.hiddenite.repository.CheckOutRepository;
import com.hiddenite.service.HandleRecievedCheckout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
public class CheckOutTrackRestController {
  @Autowired
  CheckOutRepository checkOutRepository;

  @Autowired
  HandleRecievedCheckout handleRecievedCheckout;

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  public ErrorMessage MissingBodyParamter(MethodArgumentNotValidException e, HttpServletRequest request) {
    String temp = "Missing field(s): ";
    List<FieldError> errors = e.getBindingResult().getFieldErrors();
    for (FieldError error : errors) {
      temp = temp.concat(error.getField() + ", ");
    }
    return new ErrorMessage(400,"Bad Reques!", temp);
  }

  @RequestMapping(value = "/api/checkouts",  method = RequestMethod.POST)
  @ResponseStatus(code = HttpStatus.CREATED)
  public Checkout responseToCheckout(@RequestBody @Valid Checkout recievedCheckout) {
    return handleRecievedCheckout.response(recievedCheckout);
  }
}
