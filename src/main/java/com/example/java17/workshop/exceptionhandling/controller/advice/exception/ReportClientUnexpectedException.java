package com.example.java17.workshop.exceptionhandling.controller.advice.exception;

public class ReportClientUnexpectedException extends RuntimeException {

  public ReportClientUnexpectedException(Throwable cause) {
    super(cause);
  }
}