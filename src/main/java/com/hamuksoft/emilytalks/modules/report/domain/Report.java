package com.hamuksoft.emilytalks.modules.report.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class Report {
    private String reportId;
    private String userId;
    private Date generationDate;
    private Integer grammarScore;
    private Integer vocabularyScore;
    private String feedback;
}
