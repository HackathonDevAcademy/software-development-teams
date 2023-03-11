package com.developer.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReportDTO {
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
}
