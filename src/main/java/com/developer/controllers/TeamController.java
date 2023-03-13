package com.developer.controllers;

import com.developer.dto.TeamDTO;
import com.developer.mapper.TeamMapper;
import com.developer.security.DeveloperDetails;
import com.developer.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;
    private final TeamMapper teamMapper;

    @Autowired
    public TeamController(TeamService teamService, TeamMapper teamMapper) {
        this.teamService = teamService;
        this.teamMapper = teamMapper;
    }

    @GetMapping("/current")
    public TeamDTO getByDevId(@AuthenticationPrincipal DeveloperDetails developerDetails) {
        return teamMapper.convertToDTO(
                teamService.findByDeveloperId(developerDetails.getDeveloper().getId()));
    }

    @GetMapping("/{id}")
    public TeamDTO getTeamById(@PathVariable Long id) {
        return teamMapper.convertToDTO(teamService.getTeamById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long saveTeam(@RequestBody TeamDTO teamDTO) {
        return teamService.saveTeam(teamMapper.convertToEntity(teamDTO));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Long updateTeam(@PathVariable Long id, @RequestBody TeamDTO teamDTO) {
        return teamService.updateTeam(id, teamMapper.convertToEntity(teamDTO));
    }

}

