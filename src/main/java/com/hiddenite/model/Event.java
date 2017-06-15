package com.hiddenite.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

public class Event {
  LocalDateTime time;
  final String HOST = "hotel-booking-payment";
  String message;

  public Event(String message) {
    this.message = message;
    time = LocalDateTime.now();
  }

  public LocalDateTime getTime() {
    return time;
  }

  public void setTime(LocalDateTime time) {
    this.time = time;
  }

  public String getHOST() {
    return HOST;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public static String asJsonString(final Object obj) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
