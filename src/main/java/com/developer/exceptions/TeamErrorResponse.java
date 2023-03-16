package com.developer.exceptions;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class TeamErrorResponse {
    private String message;
    private String timestamp;
}
