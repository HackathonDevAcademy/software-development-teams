package com.developer.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskDTO {
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String priority;
    private String status;
}
