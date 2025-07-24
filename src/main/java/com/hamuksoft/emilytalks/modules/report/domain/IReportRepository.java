package com.hamuksoft.emilytalks.modules.report.domain;

import java.util.List;

public interface IReportRepository {
    void save(Report report);
    Report findByUserId(String reportId);
    void deleteByUserId(String userId);
    List<Report> findAll();
}
