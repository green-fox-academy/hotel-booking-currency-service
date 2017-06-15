package com.hiddenite.controller;

import com.hiddenite.model.Status;
//import com.hiddenite.service.MQService;
import com.hiddenite.service.StatusService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.concurrent.TimeoutException;


@RestController
public class CurrencyRestController {

  static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass());

//  @Autowired
//  MQService mqService;

  @Autowired
  StatusService statusService;

  @GetMapping("/heartbeat")
  public Status getStatus(HttpServletRequest request) throws IOException, TimeoutException {
//    mqService.sendMessageToQueue("heartbeat", "Hello World!");
    Status status = statusService
            .checkStatusCondition(1, true);
    if (status.everythingIsOk()) {
      log.info("HTTP-REQUEST " + request.getRequestURI());
    } else {
      log.error("HTTP-ERROR" + request.getRequestURI());
    }
    return status;
  }

}
