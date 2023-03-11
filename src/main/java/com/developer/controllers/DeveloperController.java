package com.developer.controllers;

import com.developer.models.Developer;
import com.developer.services.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/developer")
public class DeveloperController {
    private final DeveloperService developerService;

    @Autowired
    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @GetMapping
    public List<Developer> getAllDevelopers() {
        return developerService.getAllDevelopers();
    }

    @GetMapping("/{id}")
    public Developer getDeveloperById(@PathVariable Long id) {
        return developerService.getDeveloperById(id);
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Developer createDeveloper(@RequestBody Developer developer) {
        return developerService.saveDeveloper(developer);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Long updateDeveloper(@PathVariable Long id, @RequestBody Developer developer) {
        return developerService.updateDeveloper(id, developer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteDeveloperById(@PathVariable Long id) {
        developerService.deleteDeveloperById(id);
    }
}

