package com.developer.controllers;

import com.developer.dto.ReportDTO;
import com.developer.dto.TaskDTO;
import com.developer.mapper.ReportMapper;
import com.developer.mapper.TaskMapper;
import com.developer.models.Report;
import com.developer.models.Task;
import com.developer.security.DeveloperDetails;
import com.developer.services.ReportService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;
    private final ReportMapper reportMapper;
    private final TaskMapper taskMapper;

    @Autowired
    public ReportController(ReportService reportService, ReportMapper reportMapper, TaskMapper taskMapper) {
        this.reportService = reportService;
        this.reportMapper = reportMapper;
        this.taskMapper = taskMapper;
    }

    @GetMapping("{id}")
    public ReportDTO getById(@PathVariable Long id) {
        return reportMapper.convertToDTO(reportService.getById(id));
    }

    @PostMapping("/create")
    public List<TaskDTO> createReport(@RequestBody @Valid ReportDTO reportDTO,
                                      @AuthenticationPrincipal DeveloperDetails developerDetails) {
        Report report = reportMapper.convertToEntity(reportDTO);
        return reportService.createReportByDevId(developerDetails.getDeveloper().getId(), report)
                .stream().map(taskMapper::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/excel")
    public ResponseEntity<byte[]> exportToExcel(@RequestParam String startDate,
                                                @RequestParam String endDate,
                                                @AuthenticationPrincipal DeveloperDetails developerDetails) throws IOException {
        List<Task> tasks = reportService.reportForExportByDevId(
                developerDetails.getDeveloper().getId(), startDate, endDate);

        return reportService.exportToExcel(tasks);
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> exportToPdf(@RequestParam String startDate,
                                              @RequestParam String endDate,
                                              @AuthenticationPrincipal DeveloperDetails developerDetails) throws DocumentException {
        List<Task> tasks = reportService.reportForExportByDevId(
                developerDetails.getDeveloper().getId(), startDate, endDate);

        return reportService.exportToPDF(tasks);
    }

}
