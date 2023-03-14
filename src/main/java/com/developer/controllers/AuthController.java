package com.developer.controllers;

import com.developer.dto.DeveloperDTO;
import com.developer.mapper.DeveloperMapper;
import com.developer.services.DeveloperService;
import com.developer.util.DeveloperValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.List;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class AuthController {

    private final DeveloperService developerService;
    private final DeveloperMapper developerMapper;
    private final DeveloperValidator developerValidator;

    @Autowired
    public AuthController(DeveloperService developerService, DeveloperMapper developerMapper, DeveloperValidator developerValidator) {
        this.developerService = developerService;
        this.developerMapper = developerMapper;
        this.developerValidator = developerValidator;
    }

    @GetMapping("/activate")
    public List<String> activateAccount(@RequestParam("email") String email, @RequestParam("token") String token) {
        return developerService.activateAccount(email, token);
    }

    @PostMapping("/register")
    public Object registerUser(@RequestBody @Valid DeveloperDTO developerDTO,
                                     BindingResult bindingResult) throws MessagingException {
        developerValidator.validate(developerMapper.convertToEntity(developerDTO), bindingResult);
        if(bindingResult.hasErrors())
            return bindingResult.getAllErrors();

        return developerService.registerUser(developerMapper.convertToEntity(developerDTO));
    }
}

