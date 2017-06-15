package com.hiddenite.controller;

import com.hiddenite.model.Status;
import com.hiddenite.service.MQService;
import com.hiddenite.service.StatusService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.concurrent.TimeoutException;


@RestController
public class CurrencyRestController {

  static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass());

  @Autowired
  MQService mqService;

  @Autowired
  StatusService statusService;

  @GetMapping("/heartbeat")
  public Status getStatus() throws IOException, TimeoutException {
    mqService.sendMessageToQueue("heartbeat", "Hello World!");
    Status status = statusService
            .checkStatusCondition(mqService.getQueueMessageCount("heartbeat"), mqService.isConnected());
    if (status.everythingIsOk()) {
      log.info("HTTP-REQUEST /heartbeat");
    } else {
      log.error("HTTP-ERROR /heartbeat");
    }
    return status;
  }

}
