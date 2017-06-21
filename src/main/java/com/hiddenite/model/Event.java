package com.hiddenite.model;

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
}
