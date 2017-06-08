package com.hiddenite.controller;

import com.hiddenite.model.Status;
import com.hiddenite.repository.HearthbeatRepository;
import com.hiddenite.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyRestController {

  private HearthbeatRepository hearthbeatRepository;

  @Autowired
  public CurrencyRestController(HearthbeatRepository hearthbeatRepository) {
    this.hearthbeatRepository = hearthbeatRepository;
  }

  @Autowired
  StatusService statusService;

  @GetMapping("/hearthbeat")
  public Status getStatus() {
    Status status = statusService.checkDatabaseIsEmpty();
    return status;
  }
}
