package com.developer.services;

import com.developer.models.Report;
import com.developer.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    public Report findById(Long id) {
        return reportRepository.findById(id).orElse(null);
    }

    public Long save(Report report) {
        return reportRepository.save(report).getId();
    }

    public Long deleteById(Long id) {
        reportRepository.deleteById(id);
        return id;
    }

    public Long updateReport(Long id, Report updatedReport) {
        Report report = reportRepository.findById(id).orElse(null);
        if (report == null)
            return null;

        report.setName(updatedReport.getName());
        report.setDescription(updatedReport.getDescription());
        report.setStartDate(updatedReport.getStartDate());
        report.setEndDate(updatedReport.getEndDate());
        report.setTeam(updatedReport.getTeam());
        report.setCompletedTasks(updatedReport.getCompletedTasks());

        return reportRepository.save(report).getId();
    }
}
