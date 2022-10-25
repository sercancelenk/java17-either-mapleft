package com.example.java17.workshop.exceptionhandling.controller.advice;

import com.example.java17.workshop.exceptionhandling.controller.advice.exception.*;
import com.example.java17.workshop.exceptionhandling.domain.ApiErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(DatabaseAccessException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleDatabaseAccessException(DatabaseAccessException ex) {
        return new ApiErrorResponse(500, "Internal server error");
    }

    @ExceptionHandler(ReportClientUnexpectedException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleReportClientUnexpectedException(ReportClientUnexpectedException ex) {
        return new ApiErrorResponse(500, "Internal server error");
    }

    @ExceptionHandler(ReportNameNotFoundInReportApiException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiErrorResponse handleReportNameNotFoundInReportApiException(ReportNameNotFoundInReportApiException ex) {
        return new ApiErrorResponse(400, "Invalid Report name.");
    }

    @ExceptionHandler(UserNotFoundInDbException.class)
    @ResponseStatus(NOT_FOUND)
    public ApiErrorResponse handleUserNotFoundInDbException(UserNotFoundInDbException ex) {
        return new ApiErrorResponse(404, "Provided user id doesn't exist.");
    }

    @ExceptionHandler(UserNotFoundInReportApiException.class)
    @ResponseStatus(NOT_FOUND)
    public ApiErrorResponse handleUserNotFoundInReportApiException(UserNotFoundInReportApiException ex) {
        return new ApiErrorResponse(404, "Report for given user not found.");
    }

}