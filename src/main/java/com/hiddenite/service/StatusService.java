package com.hiddenite.service;

import com.hiddenite.repository.HearthbeatRepository;
import com.hiddenite.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
