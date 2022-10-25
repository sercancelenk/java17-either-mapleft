package com.example.java17.workshop.exceptionhandling.service;

import com.example.java17.workshop.exceptionhandling.client.ReportClient;
import com.example.java17.workshop.exceptionhandling.client.exception.InvalidReportNameException;
import com.example.java17.workshop.exceptionhandling.client.exception.ReportForUserNotFoundException;
import com.example.java17.workshop.exceptionhandling.controller.advice.error.GeneralError;
import com.example.java17.workshop.exceptionhandling.controller.advice.error.ReportError;
import com.example.java17.workshop.exceptionhandling.controller.advice.exception.ReportClientUnexpectedException;
import com.example.java17.workshop.exceptionhandling.controller.advice.exception.ReportNameNotFoundInReportApiException;
import com.example.java17.workshop.exceptionhandling.controller.advice.exception.UserNotFoundInReportApiException;
import com.example.java17.workshop.exceptionhandling.domain.Report;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {
    private final ReportClient reportClient;

    public Either<GeneralError, Report> getReportV2(String email, String reportName) {
        return Try.of(() -> reportClient.getReport(email, reportName)).toEither()
                .map(a -> {
                    log.info("aaaaaaaa");
                    return a;
                })
                .mapLeft(reportServiceException ->
                        switch (reportServiceException) {
                            case InvalidReportNameException e -> new ReportError.InvalidReportNameError(reportName);
                            case ReportForUserNotFoundException e -> new ReportError.ReportForUserNotFoundError(email);
                            default -> new ReportError.ReportClientUnexpectedError(reportServiceException);
                        }
                );
    }

    public Report getReportV1(String email, String reportName) {
        try {
            return reportClient.getReport(email, reportName);
        } catch (InvalidReportNameException e) {
            throw new ReportNameNotFoundInReportApiException();
        } catch (ReportForUserNotFoundException e) {
            throw new UserNotFoundInReportApiException();
        } catch (Exception e) {
            throw new ReportClientUnexpectedException(e);
        }
    }
}
