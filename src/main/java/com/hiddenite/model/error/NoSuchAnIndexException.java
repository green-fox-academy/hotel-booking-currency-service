package com.hiddenite.model.error;

public class NoSuchAnIndexException extends Exception {
  public static String message;
  public static long index;

  public NoSuchAnIndexException(String message, long index) {
    NoSuchAnIndexException.message = message;
    NoSuchAnIndexException.index = index;
  }


  public NoSuchAnIndexException(String message) {
    super(message);
  }

}
