package com.developer.controllers;

import com.developer.dto.DeveloperDTO;
import com.developer.mapper.DeveloperMapper;
import com.developer.services.DeveloperService;
import com.developer.util.DeveloperValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

    @PostMapping("/registration")
    public Object registration(@RequestBody @Valid DeveloperDTO developerDTO, BindingResult bindingResult) {
        developerValidator.validate(developerMapper.convertToEntity(developerDTO), bindingResult);

        if(bindingResult.hasErrors())
            return bindingResult.getFieldErrors();

        return developerService.saveDeveloper(developerMapper.convertToEntity(developerDTO));
    }

}
