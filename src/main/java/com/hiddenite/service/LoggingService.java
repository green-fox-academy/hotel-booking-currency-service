package com.hiddenite.service;

import com.hiddenite.model.Status;
import java.lang.invoke.MethodHandles;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class LoggingService {

  static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass());


  public void getLogMessage(HttpServletRequest request, Status status) {

    if (status.everythingIsOk()) {
      log.info("HTTP-REQUEST " + request.getRequestURI());
    } else {
      log.error("HTTP-ERROR " + request.getRequestURI());
    }
  }
}


