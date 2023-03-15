package com.developer.controllers;

import com.developer.dto.TaskDTO;
import com.developer.mapper.TaskMapper;
import com.developer.security.DeveloperDetails;
import com.developer.services.TaskService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable Long id) {
        return taskMapper.convertToDTO(taskService.getTaskById(id));
    }

    @PostMapping
    public Long createTask(@RequestBody TaskDTO taskDTO,
                           @AuthenticationPrincipal DeveloperDetails developerDetails) {
        return taskService.saveTask(developerDetails.getDeveloper().getId(),
                taskMapper.convertToEntity(taskDTO));
    }

    @PutMapping("/{id}")
    public Long updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        return taskService.updateTask(id, taskMapper.convertToEntity(taskDTO));
    }

    @GetMapping("/report")
    public List<TaskDTO> createReport(@RequestParam String startDate,
                                      @RequestParam String endDate) {
        return taskService.createReport(startDate, endDate).stream().map(
                taskMapper::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/report/excel")
    public ResponseEntity<byte[]> exportToExcel(@RequestParam String startDate,
                                                @RequestParam String endDate) throws IOException {
        return taskService.exportToExcel(startDate, endDate);
    }

}
