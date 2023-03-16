package com.developer.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Data
public class TeamDTO {
    @NotBlank(message = "Имя команды не может быть пустым")
    @Max(value = 25, message = "Превышено максимальное значение для имени команды!")
    private String name;
    private String description;
}
