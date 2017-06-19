package com.hiddenite.service;

import com.hiddenite.model.Status;
import com.hiddenite.repository.HeartbeatRepository;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;
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

  public Status handleEndPointRequest()
      throws Exception {
    mqService.sendMessageToQueue("heartbeat", "Hello World!");
    mqService.consume("heartbeat");

    return statusService.checkStatus();
  }
}
