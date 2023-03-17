package com.developer.controllers;

import com.developer.dto.TaskDTO;
import com.developer.mapper.TaskMapper;
import com.developer.security.DeveloperDetails;
import com.developer.services.TaskService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

}
