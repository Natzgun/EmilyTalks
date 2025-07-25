package com.hamuksoft.emilytalks.modules.report.infrastructure;

import com.hamuksoft.emilytalks.modules.report.domain.Report;

public class ReportMapper {
    private ReportMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static ReportEntity toEntity(Report report) {
        ReportEntity entity = new ReportEntity();
        entity.setReportId(report.getReportId());
        entity.setUserId(report.getUserId());
        entity.setGenerationDate(report.getGenerationDate());
        entity.setGrammarScore(report.getGrammarScore());
        entity.setVocabularyScore(report.getVocabularyScore());
        entity.setFeedback(report.getFeedback());
        return entity;
    }

    public static Report toDomain(ReportEntity entity) {
        return Report.builder()
                .reportId(entity.getReportId())
                .userId(entity.getUserId())
                .generationDate(entity.getGenerationDate())
                .grammarScore(entity.getGrammarScore())
                .vocabularyScore(entity.getVocabularyScore())
                .feedback(entity.getFeedback())
                .build();
    }
}
