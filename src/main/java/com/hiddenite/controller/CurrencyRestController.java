package com.hiddenite.controller;

import com.hiddenite.model.Status;
import com.hiddenite.service.MQService;
import com.hiddenite.service.StatusService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;

//import com.hiddenite.service.MQService;


@RestController
public class CurrencyRestController {

  static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass());

  @Autowired
  MQService mqService;

  @Autowired
  StatusService statusService;

  @GetMapping("/heartbeat")
  public Status getStatus(javax.servlet.http.HttpServletRequest request) throws Exception {
    mqService.sendMessageToQueue("heartbeat", "Hello World!");
    mqService.consume("heartbeat");
    Status status = statusService
            .checkStatusCondition(mqService.getQueueMessageCount("heartbeat"), mqService.isConnected());
    if (status.everythingIsOk()) {
      log.info("HTTP-REQUEST " + request.getRequestURI());
    } else {
      log.error("HTTP-ERROR " + request.getRequestURI());
    }
    return status;
  }

}
