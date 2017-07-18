package com.hiddenite.controller;

import com.hiddenite.model.monthlyFee.MonthlyFee;
import com.hiddenite.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    return feeService.getMonthlyFee(currency, hotelID);
  }
}
