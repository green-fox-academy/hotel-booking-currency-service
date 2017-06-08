package com.hiddenite.service;

import com.hiddenite.HearthbeatRepository;
import com.hiddenite.model.Status;
import org.springframework.beans.factory.annotation.Autowired;

public class StatusService {
  @Autowired
  Status status;

  @Autowired
  HearthbeatRepository hearthbeatRepository;

  public Status checkDatabaseIsEmpty() {
    if (hearthbeatRepository.count() == 0) {
      status.setDatabase("error");
    } else {
      status.setDatabase("ok");
    }
    return status;
  }
}
