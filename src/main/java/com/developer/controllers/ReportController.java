package com.developer.controllers;

import com.developer.dto.ReportDTO;
import com.developer.mapper.ReportMapper;
import com.developer.security.DeveloperDetails;
import com.developer.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @GetMapping("/all")
    public List<ReportDTO> getByDevId(@AuthenticationPrincipal DeveloperDetails developerDetails) {
        return reportService.findByCreatedById(developerDetails.getDeveloper().getId()).stream().map(
                reportMapper::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ReportDTO getReportById(@PathVariable Long id) {
        return reportMapper.convertToDTO(reportService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createReport(@RequestBody ReportDTO reportDTO,
                             @AuthenticationPrincipal DeveloperDetails developerDetails) {
        return reportService.save(developerDetails.getDeveloper().getId(),
                reportMapper.convertToEntity(reportDTO));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Long updateReport(@RequestBody ReportDTO reportDTO, @PathVariable Long id) {
        return reportService.updateReport(id, reportMapper.convertToEntity(reportDTO));
    }

    @GetMapping("/excel")
    public ResponseEntity<byte[]> exportToExcel() throws IOException {
        return reportService.exportToExcel();
    }

    @GetMapping("/between/{amount}")
    public List<ReportDTO> getByBetween(@PathVariable Integer amount) {
        return reportService.findReportForTheWeek(amount).stream().map(
                reportMapper::convertToDTO).collect(Collectors.toList());
    }

}

