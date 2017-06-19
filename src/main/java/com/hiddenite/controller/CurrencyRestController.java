package com.hiddenite.controller;

import com.hiddenite.model.Status;
import com.hiddenite.service.EndPointService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;


@RestController
public class CurrencyRestController {

  static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass());

  @Autowired
  EndPointService endPointService;

  @GetMapping("/heartbeat")
  public Status getStatus(javax.servlet.http.HttpServletRequest request) throws Exception {
    /*if (status.everythingIsOk()) {
      log.info("HTTP-REQUEST " + request.getRequestURI());
    } else {
      log.error("HTTP-ERROR " + request.getRequestURI());
    }*/
    return endPointService.handleEndPointRequest();
  }

}
