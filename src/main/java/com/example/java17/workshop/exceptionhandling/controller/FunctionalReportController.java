package com.example.java17.workshop.exceptionhandling.controller;

import com.example.java17.workshop.exceptionhandling.controller.advice.error.GeneralError;
import com.example.java17.workshop.exceptionhandling.controller.advice.error.ReportError;
import com.example.java17.workshop.exceptionhandling.controller.advice.error.ServiceError;
import com.example.java17.workshop.exceptionhandling.controller.advice.error.UserError;
import com.example.java17.workshop.exceptionhandling.domain.ApiErrorResponse;
import com.example.java17.workshop.exceptionhandling.service.ReportService;
import com.example.java17.workshop.exceptionhandling.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping(value = "/v2", produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
@AllArgsConstructor
public class FunctionalReportController {

    private final ObjectMapper objectMapper;
    private final ReportService reportService;
    private final UserService userService;

    @GetMapping("/report")
    public ResponseEntity<String> getReport(@RequestParam String userId, @RequestParam String reportName) {
        return userService.getUserV2(userId)
                .flatMap(user -> reportService.getReportV2(user.email(), reportName))
                .mapLeft(this::mapError)
                .fold(this::createErrorResponse, this::createSuccessResponse);
    }

    private ApiErrorResponse mapError(GeneralError obj) {
        log.error("Handling error response. Error is: {}", obj);
        return switch (obj) {
            case ReportError.InvalidReportNameError e -> new ApiErrorResponse(400, "Invalid report name.");
            case ReportError.ReportForUserNotFoundError e ->
                    new ApiErrorResponse(404, "Report for given user not found.");
            case UserError.UserNotFoundError e -> new ApiErrorResponse(404, "Provided user id doesn't exist.");
            case ServiceError.DatabaseError e -> new ApiErrorResponse(500, "Internal server error.");
            case ServiceError.ReportApiError e -> new ApiErrorResponse(500, "Internal server error.");
            case ReportError.ReportClientUnexpectedError e -> new ApiErrorResponse(500, "Internal server error.");
        };
    }

    ResponseEntity<String> createSuccessResponse(Object responseBody) {
        return ResponseEntity.status(200).body(toJson(responseBody));
    }

    ResponseEntity<String> createErrorResponse(ApiErrorResponse errorResponse) {
        return ResponseEntity.status(errorResponse.statusCode()).body(toJson(errorResponse));
    }

    private String toJson(Object object) {
        return Try.of(() -> objectMapper.writeValueAsString(object))
                .getOrElseThrow(e -> new RuntimeException("Cannot deserialize response", e));
    }

}