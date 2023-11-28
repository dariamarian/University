package com.example.mobileserver.exception;

import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends RuntimeException{

  private final HttpStatus code;
  public InvalidPasswordException(String message, HttpStatus code) {
    super(message);
    this.code=code;
  }

  public HttpStatus getCode() {
    return code;
  }
}
