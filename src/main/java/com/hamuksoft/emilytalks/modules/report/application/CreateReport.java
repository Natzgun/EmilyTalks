package com.hamuksoft.emilytalks.modules.report.application;

import com.hamuksoft.emilytalks.modules.report.domain.IReportRepository;
import com.hamuksoft.emilytalks.modules.report.domain.Report;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateReport {
    private final IReportRepository reportRepository;

    public CreateReport(IReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Report execute(Report request) {
        String reportId = UUID.randomUUID().toString();
        Report report = Report.builder()
                .reportId(reportId)
                .userId(request.getUserId())
                .generationDate(request.getGenerationDate())
                .grammarScore(request.getGrammarScore())
                .vocabularyScore(request.getVocabularyScore())
                .feedback(request.getFeedback())
                .build();
        reportRepository.save(report);
        return report;
    }


}
