package com.example.java17.workshop.exceptionhandling.controller.advice.exception;

public class UserNotFoundInDbException extends RuntimeException {

  public UserNotFoundInDbException(String msg) {
    super(msg);
  }
}