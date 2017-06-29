package com.hiddenite.controller;

import com.hiddenite.model.Status;
import com.hiddenite.service.EndPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyRestController {

  @Autowired
  EndPointService endPointService;

  @GetMapping("/heartbeat")
  public Status getStatus(javax.servlet.http.HttpServletRequest request) throws Exception {
    return endPointService.handleEndPointRequest(request);
  }

}
