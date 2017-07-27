package com.hiddenite.controller;

import com.hiddenite.model.error.ErrorMessage;
import com.hiddenite.model.error.NoIndexException;
import com.hiddenite.model.error.NotValidCurrencyException;
import com.hiddenite.model.monthlyFee.MonthlyFee;
import com.hiddenite.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class MonthlyFeeController {
  private FeeService feeService;

  @Autowired
  public MonthlyFeeController(FeeService feeService) {
    this.feeService = feeService;
  }

  @GetMapping(value = "/api/hotels/{id}/fee")
  public MonthlyFee getMonthlyFee(
          @PathVariable(name = "id") Long hotelID,
          @RequestParam(name = "currency") String currency) {
    return feeService.getMonthlyFee(currency.toUpperCase(), hotelID);
  }

  @ExceptionHandler(NotValidCurrencyException.class)
  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  public ErrorMessage notValidCurrency(javax.servlet.http.HttpServletRequest request) {
    return new ErrorMessage(404, "Not found", "The requested currency is not valid");
  }
}
