package com.hiddenite.service;

import com.hiddenite.model.Status;
import com.hiddenite.repository.HeartbeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {


  public StatusService() {
  }

  @Autowired
  public StatusService(HeartbeatRepository heartbeatRepository) {
    this.heartbeatRepository = heartbeatRepository;
  }

  HeartbeatRepository heartbeatRepository;

  public Status checkStatusCondition(int queueDepth, boolean isConnected) {
    Status status = new Status();
    if (heartbeatRepository.count() == 0) {
      status.setDatabase("error");
    }
    if (queueDepth > 1 || !isConnected) {
      status.setQueue("error");
    }
    System.out.println(queueDepth);
    System.out.println(isConnected);
    return status;
  }
}
