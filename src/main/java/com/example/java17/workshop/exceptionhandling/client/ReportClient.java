package com.example.java17.workshop.exceptionhandling.client;

import com.example.java17.workshop.exceptionhandling.client.exception.InvalidReportNameException;
import com.example.java17.workshop.exceptionhandling.client.exception.ReportForUserNotFoundException;
import com.example.java17.workshop.exceptionhandling.domain.Report;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class ReportClient {

    public Report getReport(String email, String reportName) {
        int rd = ThreadLocalRandom.current().nextInt(0, 10);
        return switch (rd) {
            case 0 -> throw new InvalidReportNameException();
            case 1 -> throw new ReportForUserNotFoundException();
            default -> new Report("someReportValue for " + email + " and " + reportName);
        };
    }

}