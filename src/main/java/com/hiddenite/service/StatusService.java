package com.hiddenite.service;

import com.hiddenite.model.Status;
import com.hiddenite.repository.HearthbeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

  @Autowired
  public StatusService(HearthbeatRepository hearthbeatRepository) {
    this.hearthbeatRepository = hearthbeatRepository;
  }

  HearthbeatRepository hearthbeatRepository;

  public Status checkDatabaseIsEmpty() {
    Status status = new Status();
    if (hearthbeatRepository.count() == 0) {
      status.setDatabase("error");
    } else {
      status.setDatabase("ok");
    }
    return status;
  }
}
