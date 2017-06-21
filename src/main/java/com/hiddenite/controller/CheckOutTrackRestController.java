package com.hiddenite.controller;

import com.hiddenite.model.ErrorMessage;
import com.hiddenite.model.checkout.Checkout;
import com.hiddenite.model.checkout.CheckoutLinks;
import com.hiddenite.repository.CheckOutRepository;
import com.hiddenite.service.HandleRecievedCheckout;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
public class CheckOutTrackRestController {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(code = HttpStatus.CREATED)
  public ErrorMessage MissingBodyParamter(MethodArgumentNotValidException e, HttpServletRequest request) {
    String temp = "Missing field(s): ";
    List<FieldError> errors = e.getBindingResult().getFieldErrors();
    for (FieldError error : errors) {
      temp = temp.concat(error.getField() + ", ");
    }
    return new ErrorMessage(400,"Bad Reques!", temp);
  }

  @Autowired
  CheckOutRepository checkOutRepository;

  @Autowired
  HandleRecievedCheckout handleRecievedCheckout;

  @RequestMapping(value = "/api/checkouts",  method = RequestMethod.POST)
  @ResponseStatus(code = HttpStatus.CREATED)
  public Checkout responseToCheckout(@RequestBody @Valid Checkout recievedCheckout) {
    checkOutRepository.save(recievedCheckout);
    recievedCheckout.setLinks(new CheckoutLinks(recievedCheckout.getId()));
    checkOutRepository.save(recievedCheckout);
    return recievedCheckout;
  }

}
