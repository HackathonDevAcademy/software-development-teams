package com.developer.controllers;

import com.developer.services.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password")
public class PasswordResetController {

    private final DeveloperService developerService;

    @Autowired
    public PasswordResetController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestParam("email") String email) {
        return developerService.resetPassword(email);
    }

    @PostMapping("/reset/{resetToken}")
    public ResponseEntity<?> saveNewPassword(@PathVariable("resetToken") String resetToken, @RequestParam String newPassword) {
        return developerService.saveNewPassword(resetToken, newPassword);
    }

}


