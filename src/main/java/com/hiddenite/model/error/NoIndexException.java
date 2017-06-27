package com.hiddenite.model.error;

public class NoIndexException extends Exception {
  public static String message;
  public static long index;

  public NoIndexException(String message) {
    super(message);
  }

  public NoIndexException(String message, long index) {
    this.message = message;
    this.index = index;
  }

}
