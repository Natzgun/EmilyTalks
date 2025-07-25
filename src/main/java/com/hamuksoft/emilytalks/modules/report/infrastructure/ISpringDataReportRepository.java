package com.hamuksoft.emilytalks.modules.report.infrastructure;

import com.hamuksoft.emilytalks.modules.report.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ISpringDataReportRepository extends JpaRepository<ReportEntity, String> {
    void save(Report report);
    Optional<ReportEntity> findByUserId(String reportId);
    void deleteByUserId(String userId);
    List<ReportEntity> findAll();
}
