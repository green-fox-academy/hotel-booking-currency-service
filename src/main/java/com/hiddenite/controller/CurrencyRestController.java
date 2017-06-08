package com.hiddenite.controller;

import com.hiddenite.model.Status;
import com.hiddenite.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyRestController {
  @Autowired
  StatusService statusService;

  @GetMapping("/hearthbeat")
  public Status getStatus() {
    Status status = statusService.checkDatabaseIsEmpty();
    return status;
  }
}
