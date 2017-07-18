package com.hiddenite.controller;

import com.hiddenite.model.monthlyFee.MonthlyFee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MonthlyFeeController {

  @GetMapping(value = "/api/hotel/{id}/fee")
  public MonthlyFee getMonthlyFee(
          @PathVariable(name = "id") Long hotelID,
          @RequestParam(name = "currency") String currency) {
    return new MonthlyFee();
  }
}
