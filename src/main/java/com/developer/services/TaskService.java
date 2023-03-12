package com.developer.services;

import com.developer.enums.TaskStatus;
import com.developer.models.Task;
import com.developer.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    public Long save(Task task) {
        task.setStatus(TaskStatus.NEW);
        return taskRepository.save(task).getId();
    }

    public Long updateTask(Long id, Task updatedTask) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task == null)
            return null;

        task.setName(updatedTask.getName());
        task.setDescription(updatedTask.getDescription());
        task.setStartDate(updatedTask.getStartDate());
        task.setEndDate(updatedTask.getEndDate());
        task.setPriority(updatedTask.getPriority());
        task.setStatus(updatedTask.getStatus());
        task.setProject(updatedTask.getProject());
        task.setDeveloper(updatedTask.getDeveloper());

        return taskRepository.save(task).getId();
    }

    public Long deleteById(Long id) {
        taskRepository.deleteById(id);
        return id;
    }
}
