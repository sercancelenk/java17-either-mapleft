package com.example.java17.workshop.exceptionhandling.controller.advice.exception;

public class DatabaseAccessException extends RuntimeException {

  public DatabaseAccessException(Throwable cause) {
    super(cause);
  }
}