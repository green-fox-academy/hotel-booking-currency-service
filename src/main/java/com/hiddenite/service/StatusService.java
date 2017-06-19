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
  private Status status;

  public StatusService() {
  }

  @Autowired
  public StatusService(HeartbeatRepository heartbeatRepository, MQService mqService) {
    this.heartbeatRepository = heartbeatRepository;
    this.mqService = mqService;
    status = new Status();
  }

  public Status checkStatus() throws IOException {
    status.setDatabase((heartbeatRepository.count() == 0) ? "error" : "ok");
    status.setQueue((mqService.getQueueMessageCount("heartbeat") > 1) ? "error" : "ok");
    return status;
  }

  public boolean everythingIsOk() {
    return (status.getStatus().equals("ok") && status.getDatabase().equals("ok") && status.getQueue().equals("ok"));
  }

}
