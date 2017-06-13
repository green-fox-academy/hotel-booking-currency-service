package com.hiddenite.service;

import com.hiddenite.model.Status;
import com.hiddenite.repository.HeartbeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

  @Autowired
  public StatusService(HeartbeatRepository heartbeatRepository) {
    this.heartbeatRepository = heartbeatRepository;
  }

  HeartbeatRepository heartbeatRepository;

  public Status checkDatabaseIsEmpty() {
    Status status = new Status();
    if (heartbeatRepository.count() == 0) {
      status.setDatabase("error");
    } else {
      status.setDatabase("ok");
    }
    return status;
  }
}
