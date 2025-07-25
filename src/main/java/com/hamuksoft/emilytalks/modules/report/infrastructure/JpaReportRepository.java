package com.hamuksoft.emilytalks.modules.report.infrastructure;

import com.hamuksoft.emilytalks.modules.report.domain.IReportRepository;
import com.hamuksoft.emilytalks.modules.report.domain.Report;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaReportRepository implements IReportRepository {

    private final ISpringDataReportRepository repository;

    public JpaReportRepository(ISpringDataReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Report report) {
        ReportEntity entity = ReportMapper.toEntity(report);
        repository.save(entity);
    }

    @Override
    public Optional<Report> findByUserId(String userId) {
        return repository.findByUserId(userId)
                .map(ReportMapper::toDomain);
    }

    @Override
    public void deleteByUserId(String userId) {
        repository.deleteByUserId(userId);
    }

    @Override
    public List<Report> findAll() {
        return repository.findAll()
                .stream()
                .map(ReportMapper::toDomain)
                .toList();
    }
}
