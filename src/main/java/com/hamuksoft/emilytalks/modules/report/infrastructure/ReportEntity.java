package com.hamuksoft.emilytalks.modules.report.infrastructure;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "reports")
@Data
public class ReportEntity {
    @Id
    @Column(name = "report_id", nullable = false, unique = true)
    private String reportId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "generation_date", nullable = false)
    private java.util.Date generationDate;

    @Column(name = "grammar_score")
    private Integer grammarScore;

    @Column(name = "vocabulary_score")
    private Integer vocabularyScore;

    @Column(name = "feedback")
    @Lob
    private String feedback;
}
