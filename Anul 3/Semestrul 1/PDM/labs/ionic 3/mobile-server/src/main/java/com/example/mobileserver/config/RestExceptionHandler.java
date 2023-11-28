package com.example.mobileserver.config;

import com.example.mobileserver.exception.ErrorDTO;
import com.example.mobileserver.exception.InvalidPasswordException;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(value = {InvalidPasswordException.class})
  @ResponseBody
  public ResponseEntity<ErrorDTO> handleException(InvalidPasswordException ex){
    return ResponseEntity.status(ex.getCode())
        .body(ErrorDTO.builder().message(ex.getMessage()).build());
  }
}
