package com.hiddenite.controller;

import com.hiddenite.model.exchangerates.ExchangeRate;
import com.hiddenite.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeController {
  @Autowired
  ExchangeRateService exchangeRateService;

  @GetMapping("/exchange")
  public ExchangeRate getex() {
    return exchangeRateService.saveExChangeDTOsFromExchangeRates();
  }
}
