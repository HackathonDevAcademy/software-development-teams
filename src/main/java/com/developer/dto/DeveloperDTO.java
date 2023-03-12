package com.developer.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class DeveloperDTO {
    @NotBlank(message = "Имя не может быть пустым!")
    private String fullName;
    private String position;
    @Email(message = "Не корректный email!")
    @NotBlank(message = "Email не может быть пустым!")
    private String email;
    @NotBlank(message = "Пароль не может быть пустым!")
    private String password;
}
