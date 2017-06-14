package com.hiddenite.controller;

import com.hiddenite.model.Status;
import com.hiddenite.repository.HeartbeatRepository;
import com.hiddenite.service.MQService;
import com.hiddenite.service.StatusService;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;


@RestController
public class CurrencyRestController {
  public CurrencyRestController() {
  }

  private HeartbeatRepository heartbeatRepository;
  static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass());
//
//  @Autowired
//  MQService mqService;
//
//
//  @Autowired
//  public CurrencyRestController(HeartbeatRepository heartbeatRepository) {
//    this.heartbeatRepository = heartbeatRepository;
//  }
//
//  @Autowired
//  StatusService statusService;
//
//  @GetMapping("/heartbeat")
//  public Status getStatus() throws IOException, TimeoutException {
//    mqService.sendMessageToQueue("heartbeat", "Hello World!");
//    Status status = statusService.checkStatusCondition(mqService.getQueueMessageCount("heartbeat"));
////    if (status.everythingIsOk()) {
////      log.info("HTTP-REQUEST " + httpServletRequest.getRequestURI());
////    } else {
////      log.error("HTTP-ERROR " + httpServletRequest.getRequestURI());
////    }
//    return status;
//  }

  @GetMapping("/test")
  public String gettest() throws IOException, TimeoutException {
    return "test";
  }
}
