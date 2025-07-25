package com.hamuksoft.emilytalks.modules.report.domain;

import java.util.List;
import java.util.Optional;

public interface IReportRepository {
    void save(Report report);
    Optional<Report> findByUserId(String reportId);
    void deleteByUserId(String userId);
    List<Report> findAll();
}
