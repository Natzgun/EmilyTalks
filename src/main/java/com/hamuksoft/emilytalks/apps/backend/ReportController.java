package com.hamuksoft.emilytalks.apps.backend;

import com.hamuksoft.emilytalks.modules.report.application.CreateReport;
import com.hamuksoft.emilytalks.modules.report.domain.Report;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final CreateReport createReport;
    public ReportController(CreateReport createReport) {
        this.createReport = createReport;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateReport(@RequestBody Report report) {
        Report generatedReport = createReport.execute(report);
        return new ResponseEntity<>("Report generated successfully with ID: " + generatedReport.getReportId(), HttpStatus.CREATED);
    }
}
