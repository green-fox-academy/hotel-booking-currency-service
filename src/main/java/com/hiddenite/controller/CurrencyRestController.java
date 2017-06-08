package com.hiddenite.controller;

import com.hiddenite.model.Status;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyRestController {

  @GetMapping("/hearthbeat")
  public Status getStatus() {
    return new Status();
  }
}
