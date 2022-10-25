package com.example.java17.workshop.exceptionhandling.controller.advice.error;

public sealed interface UserError extends GeneralError {
   record UserNotFoundError(String userId) implements UserError { }
}