package com.hiddenite.service;

import com.google.gson.Gson;
import com.hiddenite.model.ErrorMessage;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

@Service
public class ErrorMessageHandler {

    public ErrorMessage getErrorMessageWithMissingFields(MethodArgumentNotValidException e) {
    Gson gson = new Gson();
    String temp = "The attribute fields: ";
    List<FieldError> errors = e.getBindingResult().getFieldErrors();
    for (FieldError error : errors) {
      temp = temp.concat(gson.toJson(error.getField()) + ", ");
    }
    temp = temp.concat(" are missing");
    return new ErrorMessage(400, "Bad Request!", temp);
  }
}
