package com.hiddenite.service;

import com.hiddenite.model.Status;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.lang.invoke.MethodHandles;

public class LoggerInterceptor extends HandlerInterceptorAdapter {

  @Autowired
  StatusService statusService;

  static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass());

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//    if (statusService.everythingIsOk()) {
      log.info("HTTP-REQUEST " + request.getRequestURI());
//    } else {
//      log.error("HTTP-ERROR " + request.getRequestURI());
//    }
  }

}
