package com.developer.controllers;

import com.developer.dto.ReportDTO;
import com.developer.mapper.ReportMapper;
import com.developer.models.Report;
import com.developer.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;
    private final ReportMapper reportMapper;

    @Autowired
    public ReportController(ReportService reportService, ReportMapper reportMapper) {
        this.reportService = reportService;
        this.reportMapper = reportMapper;
    }

    @GetMapping
    public List<ReportDTO> getAllReports() {
        return reportService.findAll().stream().map(
                reportMapper::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ReportDTO getReportById(@PathVariable Long id) {
        return reportMapper.convertToDTO(reportService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createReport(@RequestBody Report report) {
        return reportService.save(report);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Long updateReport(@RequestBody Report report, @PathVariable Long id) {
        return reportService.updateReport(id, report);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Long deleteReportById(@PathVariable Long id) {
        return reportService.deleteById(id);
    }
}

