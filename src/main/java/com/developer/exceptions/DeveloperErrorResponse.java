package com.developer.exceptions;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class DeveloperErrorResponse {
    private String message;
    private String timestamp;
}
