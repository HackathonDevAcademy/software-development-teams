package com.developer.controllers.advice;

import com.developer.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final DeveloperErrorResponse developerErrorResponse;
    private final TeamErrorResponse teamErrorResponse;
    private final TaskErrorResponse taskErrorResponse;
    private final ReportErrorResponse reportErrorResponse;

    @Autowired
    public GlobalExceptionHandler(DeveloperErrorResponse developerErrorResponse,
                                  TeamErrorResponse teamErrorResponse, TaskErrorResponse taskErrorResponse, ReportErrorResponse reportErrorResponse) {
        this.developerErrorResponse = developerErrorResponse;
        this.teamErrorResponse = teamErrorResponse;
        this.taskErrorResponse = taskErrorResponse;
        this.reportErrorResponse = reportErrorResponse;
    }

    @ExceptionHandler
    private ResponseEntity<DeveloperErrorResponse> handleException(DeveloperNotFoundException e) {
        developerErrorResponse.setMessage("Пользователь с таким id не найден!");
        developerErrorResponse.setTimestamp(LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
        return new ResponseEntity<>(developerErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<TeamErrorResponse> handleException(TeamNotFountException e) {
        teamErrorResponse.setMessage("Команда с таким id не найдена!");
        teamErrorResponse.setTimestamp(LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
        return new ResponseEntity<>(teamErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<TaskErrorResponse> handleException(TaskNotFoundException e) {
        taskErrorResponse.setMessage("Задача с таким id не найдена!");
        taskErrorResponse.setTimestamp(LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
        return new ResponseEntity<>(taskErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ReportErrorResponse> handleException(ReportNotFoundException e) {
        reportErrorResponse.setMessage("Отчет с таким id не найден!");
        reportErrorResponse.setTimestamp(LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
        return new ResponseEntity<>(reportErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }
}

