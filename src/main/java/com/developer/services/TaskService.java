package com.developer.services;

import com.developer.enums.TaskStatus;
import com.developer.exceptions.DeveloperNotFoundException;
import com.developer.exceptions.TaskNotFoundException;
import com.developer.models.Task;
import com.developer.repositories.DeveloperRepository;
import com.developer.repositories.TaskRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final DeveloperRepository developerRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, DeveloperRepository developerRepository) {
        this.taskRepository = taskRepository;
        this.developerRepository = developerRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
    }

    public Long saveTask(Long id, Task task) {
        task.setDeveloper(developerRepository.findById(id).orElseThrow(DeveloperNotFoundException::new));
        task.setStatus(TaskStatus.NEW);
        return taskRepository.save(task).getId();
    }

    public Long deleteTask(Long id) {
        taskRepository.deleteById(id);
        return id;
    }

    public Long updateTask(Long id, Task newTask) {
        Task taskToUpdate = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        if (taskToUpdate == null)
            return null;

        taskToUpdate.setTitle(newTask.getTitle());
        taskToUpdate.setDescription(newTask.getDescription());
        taskToUpdate.setStartDate(newTask.getStartDate());
        taskToUpdate.setEndDate(newTask.getEndDate());
        taskToUpdate.setStatus(newTask.getStatus());
        taskToUpdate.setDeveloper(newTask.getDeveloper());

        return taskRepository.save(taskToUpdate).getId();
    }

    public List<Task> createReport(String startDate, String endDate) {
        return taskRepository.createReport(startDate, endDate);
    }

    public List<Task> createReportByDevId(Long id, String startDate, String endDate) {
        return taskRepository.createReportByDevId(id, startDate, endDate);
    }

    public ResponseEntity<byte[]> exportToExcel(List<Task> tasks) throws IOException {
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
        headerRow.createCell(1).setCellValue("Title");
        headerRow.createCell(2).setCellValue("Description");
        headerRow.createCell(3).setCellValue("Start_date");
        headerRow.createCell(4).setCellValue("End_date");
        headerRow.createCell(5).setCellValue("Status");
        headerRow.createCell(6).setCellValue("Owner");

        // Устанавливаем стиль заголовков
        for (Cell cell : headerRow) {
            cell.setCellStyle(headerStyle);
        }

        // Заполняем таблицу данными
        int rowNum = 1;
        for (Task task : tasks) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(task.getId());
            row.createCell(1).setCellValue(task.getTitle());
            row.createCell(2).setCellValue(task.getDescription());
            row.createCell(3).setCellValue(task.getStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
            row.createCell(4).setCellValue(task.getEndDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
            row.createCell(5).setCellValue(String.valueOf(task.getStatus()));
            row.createCell(6).setCellValue(task.getDeveloper().getFullName());
        }

        // Авторазмер столбцов
        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }

        // Переводим книгу Excel в массив байтов
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);


        String fileName = "report on tasks " +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        // Формируем ответ с файлом Excel
        byte[] bytes = outputStream.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName + ".xlsx");
        headers.setContentLength(bytes.length);
        return new ResponseEntity<>(bytes, headers, 200);
    }

    public ResponseEntity<byte[]> exportToPDF(List<Task> tasks) throws DocumentException {
        // Создаем документ PDF
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // Создаем таблицу и задаем заголовки столбцов
        PdfPTable table = new PdfPTable(7);
        table.setWidths(new int[]{1, 2, 3, 2, 2, 1, 2});
        table.addCell("ID");
        table.addCell("Title");
        table.addCell("Description");
        table.addCell("Start_date");
        table.addCell("End_date");
        table.addCell("Status");
        table.addCell("Owner");

        // Заполняем таблицу данными
        for (Task task : tasks) {
            table.addCell(String.valueOf(task.getId()));
            table.addCell(task.getTitle());
            table.addCell(task.getDescription());
            table.addCell(task.getStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
            table.addCell(task.getEndDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
            table.addCell(String.valueOf(task.getStatus()));
            table.addCell(task.getDeveloper().getFullName());
        }

        // Добавляем таблицу в документ PDF
        document.add(table);
        document.close();

        String fileName = "report on tasks " +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        // Формируем ответ с файлом PDF
        byte[] bytes = outputStream.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", fileName + ".pdf");
        headers.setContentLength(bytes.length);
        return new ResponseEntity<>(bytes, headers, 200);
    }

}

