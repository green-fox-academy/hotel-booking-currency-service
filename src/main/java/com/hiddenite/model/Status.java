package com.hiddenite.model;

public class Status {
  String status;
  String database;
  String queue;

  public String getQueue() {
    return queue;
  }

  public void setQueue(String queue) {
    this.queue = queue;
  }

  public Status() {
    status = "ok";
    database = "ok";
    queue = "ok";
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }


  public String getDatabase() {
    return database;
  }

  public void setDatabase(String database) {
    this.database = database;
  }

  public boolean everythingIsOk() {
    return (status.equals("ok") && database.equals("ok") && queue.equals("ok"));
  }
}
