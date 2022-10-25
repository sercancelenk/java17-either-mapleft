package com.example.java17.workshop.exceptionhandling.controller.advice.error;

public sealed interface GeneralError permits ReportError, UserError, ServiceError {

}