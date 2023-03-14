package com.developer.services;

import com.developer.models.Report;
import com.developer.repositories.DeveloperRepository;
import com.developer.repositories.ReportRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
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

    public List<Report> findReportForTheWeek(Integer between) {
        return reportRepository.findReportForTheWeek(between);
    }

    public ResponseEntity<byte[]> exportToExcel() throws IOException {
        List<Report> reports = reportRepository.findAll();

        // Создаем книгу Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reports");

        // Задаем стиль заголовков
        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        // Создаем заголовки столбцов
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Description");
        headerRow.createCell(3).setCellValue("Date");
        headerRow.createCell(4).setCellValue("Developer");

        // Устанавливаем стиль заголовков
        for (Cell cell : headerRow) {
            cell.setCellStyle(headerStyle);
        }

        // Заполняем таблицу данными
        int rowNum = 1;
        for (Report report : reports) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(report.getId());
            row.createCell(1).setCellValue(report.getName());
            row.createCell(2).setCellValue(report.getDescription());
            row.createCell(3).setCellValue(report.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            row.createCell(4).setCellValue(report.getDeveloper().getFullName());
        }

        // Авторазмер столбцов
        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }

        // Переводим книгу Excel в массив байтов
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);

        // Формируем ответ с файлом Excel
        byte[] bytes = outputStream.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "reports.xlsx");
        headers.setContentLength(bytes.length);
        return new ResponseEntity<>(bytes, headers, 200);
    }
}
