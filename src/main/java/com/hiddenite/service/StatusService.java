package com.hiddenite.service;

import com.hiddenite.model.Status;
import com.hiddenite.repository.HeartbeatRepository;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

  private HeartbeatRepository heartbeatRepository;

  private MQService mqService;

  public StatusService() {
  }

  @Autowired
  public StatusService(HeartbeatRepository heartbeatRepository, MQService mqService) {
    this.heartbeatRepository = heartbeatRepository;
    this.mqService = mqService;
  }

  public Status checkStatus() throws IOException {


    Status status = new Status();
    if (heartbeatRepository.count() == 0) {
      status.setDatabase("error");
    }
    if (mqService.getQueueMessageCount("heartbeat") > 1 || !mqService.isConnected()) {
      status.setQueue("error");
    }

    return status;
  }

}
