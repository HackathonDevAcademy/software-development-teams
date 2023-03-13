package com.developer.controllers;

import com.developer.dto.DeveloperDTO;
import com.developer.mapper.DeveloperMapper;
import com.developer.security.DeveloperDetails;
import com.developer.services.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/current")
    public DeveloperDTO getDeveloperById(@AuthenticationPrincipal DeveloperDetails developerDetails) {
        return developerMapper.convertToDTO(
                developerService.getDeveloperById(developerDetails.getDeveloper().getId()));
    }

    @GetMapping("/team/{id}")
    public List<DeveloperDTO> getTeamDevelopers(@PathVariable Long id) {
        return developerService.findByTeamId(id).stream().map(
                developerMapper::convertToDTO).collect(Collectors.toList());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Long updateDeveloper(@AuthenticationPrincipal DeveloperDetails developerDetails, @RequestBody DeveloperDTO developerDTO) {
        return developerService.updateDeveloper(developerDetails.getDeveloper().getId(),
                developerMapper.convertToEntity(developerDTO));
    }

}

