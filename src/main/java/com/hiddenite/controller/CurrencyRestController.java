package com.hiddenite.controller;

import com.hiddenite.model.Status;
import com.hiddenite.repository.HeartbeatRepository;
import com.hiddenite.service.StatusService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;

@RestController
public class CurrencyRestController {

  private HeartbeatRepository heartbeatRepository;
  static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass());


  @Autowired
  public CurrencyRestController(HeartbeatRepository heartbeatRepository) {
    this.heartbeatRepository = heartbeatRepository;
  }

  @Autowired
  StatusService statusService;

  @GetMapping("/heartbeat")
  public Status getStatus() {
    Status status = statusService.checkDatabaseIsEmpty();
    log.info("LOGGING TEST MESSAGE");
    return status;
  }
}
