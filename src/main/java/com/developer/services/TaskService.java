package com.developer.services;

import com.developer.models.Developer;
import com.developer.models.Task;
import com.developer.repositories.DeveloperRepository;
import com.developer.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        taskToUpdate.setReport(newTask.getReport());

        return taskRepository.save(taskToUpdate).getId();
    }

}







        }
