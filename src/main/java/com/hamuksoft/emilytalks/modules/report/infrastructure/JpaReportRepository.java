package com.hamuksoft.emilytalks.modules.report.infrastructure;

import com.hamuksoft.emilytalks.modules.report.domain.IReportRepository;
import com.hamuksoft.emilytalks.modules.report.domain.Report;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaReportRepository implements IReportRepository {

    @Override
    public void save(Report report) {
        // Implementation for saving the report entity to the database
    }

    @Override
    public Report findByUserId(String userId) {
        // Implementation for finding a report by user ID
        return null; // Placeholder return statement
    }

    @Override
    public void deleteByUserId(String userId) {
        // Implementation for deleting a report by user ID
    }

    @Override
    public List<Report> findAll() {
        // Implementation for finding all reports
        return null; // Placeholder return statement
    }
}
