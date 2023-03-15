package com.developer.services;

import com.developer.enums.TaskStatus;
import com.developer.models.Task;
import com.developer.repositories.DeveloperRepository;
import com.developer.repositories.TaskRepository;
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
        return taskRepository.findById(id).orElse(null);
    }

    public Long saveTask(Long id, Task task) {
        task.setDeveloper(developerRepository.findById(id).orElse(null));
        task.setStatus(TaskStatus.NEW);
        return taskRepository.save(task).getId();
    }

    public Long deleteTask(Long id) {
        taskRepository.deleteById(id);
        return id;
    }

    public Long updateTask(Long id, Task newTask) {
        Task taskToUpdate = taskRepository.findById(id).orElse(null);
        if(taskToUpdate == null)
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

    public ResponseEntity<byte[]> exportToExcel(String startDate, String endDate) throws IOException {
        List<Task> tasks = taskRepository.createReport(startDate, endDate);
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


        // Формируем ответ с файлом Excel
        byte[] bytes = outputStream.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "reports.xlsx");
        headers.setContentLength(bytes.length);
        return new ResponseEntity<>(bytes, headers, 200);
    }

}

