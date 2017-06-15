package com.hiddenite.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by Levente on 2017. 06. 15..
 */
public class Event {

  private LocalDateTime time;
  private final String hostname = "hotel-booking-payment.herokuapp.com";
  private String message;

  public Event(String message) {
    time  = LocalDateTime.now();
    this.message = message;
  }

  public LocalDateTime getTime() {
    return time;
  }

  public void setTime(LocalDateTime time) {
    this.time = time;
  }

  public String getHostname() {
    return hostname;
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
