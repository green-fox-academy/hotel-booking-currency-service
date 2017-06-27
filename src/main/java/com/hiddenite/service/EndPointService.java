package com.hiddenite.service;

import com.hiddenite.model.Status;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EndPointService {

  @Autowired
  MQService mqService;

  @Autowired
  StatusService statusService;

  public EndPointService() {
  }

  public Status handleEndPointRequest(HttpServletRequest request)
      throws Exception {
    mqService.sendMessageToQueue("heartbeat", "Hello World!");
    mqService.consume("heartbeat");
    return statusService.checkStatus();
  }
}
