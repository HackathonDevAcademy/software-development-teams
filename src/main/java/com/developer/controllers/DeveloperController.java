package com.developer.controllers;

import com.developer.dto.DeveloperDTO;
import com.developer.mapper.DeveloperMapper;
import com.developer.models.Developer;
import com.developer.services.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/developer")
public class DeveloperController {

    private final DeveloperService developerService;
    private final DeveloperMapper developerMapper;

    @Autowired
    public DeveloperController(DeveloperService developerService, DeveloperMapper developerMapper) {
        this.developerService = developerService;
        this.developerMapper = developerMapper;
    }

    @GetMapping
    public List<DeveloperDTO> getAllDevelopers() {
        return developerService.getAllDevelopers().stream().map(
                developerMapper::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public DeveloperDTO getDeveloperById(@PathVariable Long id) {
        return developerMapper.convertToDTO(developerService.getDeveloperById(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Long updateDeveloper(@PathVariable Long id, @RequestBody Developer developer) {
        return developerService.updateDeveloper(id, developer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Long deleteDeveloperById(@PathVariable Long id) {
        return developerService.deleteDeveloperById(id);
    }
}

