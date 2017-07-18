package com.hiddenite.controller;

import com.hiddenite.model.Checkouts;
import com.hiddenite.model.HotelBalance;
import com.hiddenite.model.MonthlyFee;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
public class MonthlyFeeController {

  @GetMapping(value = "/api/hotel/{id}/fee")
  public MonthlyFee getMonthlyFee(
          @PathVariable(name = "id") Long hotelID,
          @RequestParam(name = "currency") String currency) {
    return new MonthlyFee();
  }
}
