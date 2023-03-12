package com.developer.controllers;

import com.developer.models.Developer;
import com.developer.annotations.CurrentDeveloper;
import com.developer.security.DeveloperDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }

    @GetMapping("/showUserInfo")
    public String showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        DeveloperDetails developerDetails = (DeveloperDetails) authentication.getPrincipal();

        return developerDetails.getDeveloper().getFullName() + ", " + developerDetails.getUsername();
    }

    @GetMapping("/test")
    public String test(@AuthenticationPrincipal DeveloperDetails developerDetails) {
        return developerDetails.getDeveloper().getFullName() + ", " + developerDetails.getUsername();
    }

    @GetMapping("/test2")
    public String test2(@CurrentDeveloper Developer developer) {
        return developer.getFullName() + ", " + developer.getEmail();
    }

}
