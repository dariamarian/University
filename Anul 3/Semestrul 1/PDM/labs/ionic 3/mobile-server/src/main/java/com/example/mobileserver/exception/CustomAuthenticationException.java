package com.example.mobileserver.exception;

import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationException extends RuntimeException {

  public CustomAuthenticationException(String message) {
    super(message);
  }
}