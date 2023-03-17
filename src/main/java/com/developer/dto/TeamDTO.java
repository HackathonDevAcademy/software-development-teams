package com.developer.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TeamDTO {
    @NotBlank(message = "Имя команды не может быть пустым")
    private String name;

    private String description;
}
