package com.developer.dto;

import com.developer.enums.TaskStatus;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Data
public class TaskDTO {
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String priority;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
}
