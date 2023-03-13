package com.developer.services;

import com.developer.models.Report;
import com.developer.repositories.DeveloperRepository;
import com.developer.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final DeveloperRepository developerRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository, DeveloperRepository developerRepository) {
        this.reportRepository = reportRepository;
        this.developerRepository = developerRepository;
    }

    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    public Report findById(Long id) {
        return reportRepository.findById(id).orElse(null);
    }

    public Long save(Long id, Report report) {
        report.setDeveloper(developerRepository.findById(id).orElse(null));
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
        report.setDate(updatedReport.getDate());
        report.setDeveloper(updatedReport.getDeveloper());
//        report.setStartDate(updatedReport.getStartDate());
//        report.setEndDate(updatedReport.getEndDate());
//        report.setTeam(updatedReport.getTeam());
//        report.setCompletedTasks(updatedReport.getCompletedTasks());

        return reportRepository.save(report).getId();
    }

    public List<Report> findByCreatedById(Long id) {
        return reportRepository.findByDeveloperId(id);
    }
}
