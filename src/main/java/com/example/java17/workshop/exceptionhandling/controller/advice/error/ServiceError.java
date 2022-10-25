package com.example.java17.workshop.exceptionhandling.controller.advice.error;

public sealed interface ServiceError extends GeneralError {
   record DatabaseError(Throwable cause) implements ServiceError { }
   record ReportApiError(Throwable cause) implements ServiceError { }
}