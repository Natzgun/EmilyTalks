package com.hamuksoft.emilytalks.modules.report.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ISpringDataReportRepository implements JpaRepository<ReportEntity, String> {
}
