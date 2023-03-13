package com.developer.util;

import com.developer.models.Developer;
import com.developer.services.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DeveloperValidator implements Validator {
    private final DeveloperService developerService;

    @Autowired
    public DeveloperValidator(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Developer.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Developer developer = (Developer) target;

        if (developerService.findByEmail(developer.getEmail()).isPresent())
            errors.rejectValue("email", "", "Email уже зарегистрирован!");
    }
}
