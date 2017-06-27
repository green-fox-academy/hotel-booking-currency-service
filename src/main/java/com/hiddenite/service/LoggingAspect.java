package com.hiddenite.service;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

@Aspect
@Service
public class LoggingAspect {

  static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass());

  @After("execution( * com.hiddenite.controller..*(..)) && args(.., request))")
  public void loggingAdvice(HttpServletRequest request) throws IOException {
    log.info("HTTP-REQUEST " + request.getRequestURI());
  }

  @After("execution(@org.springframework.web.bind.annotation.ExceptionHandler * *(..)) && args(.., request)")
  public void loggingExceptionAdvice(HttpServletRequest request) {
    log.error("HTTP-ERROR " + request.getRequestURI());
  }

}


