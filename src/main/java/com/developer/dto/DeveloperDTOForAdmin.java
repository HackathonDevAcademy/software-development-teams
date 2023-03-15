package com.developer.dto;

import com.developer.enums.DevStatus;
import com.developer.enums.Role;
import lombok.Data;

@Data
public class DeveloperDTOForAdmin {
    private String fullName;
    private String email;
    private String password;
    private String position;
    private Role role;
    private DevStatus status;
}
