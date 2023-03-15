package com.developer.controllers;

import com.developer.dto.DeveloperDTO;
import com.developer.dto.ReportDTO;
import com.developer.dto.TaskDTO;
import com.developer.dto.TeamDTO;
import com.developer.mapper.DeveloperMapper;
import com.developer.mapper.ReportMapper;
import com.developer.mapper.TaskMapper;
import com.developer.mapper.TeamMapper;
import com.developer.services.DeveloperService;
import com.developer.services.ReportService;
import com.developer.services.TaskService;
import com.developer.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final DeveloperService developerService;
    private final DeveloperMapper developerMapper;
    private final ReportService reportService;
    private final ReportMapper reportMapper;
    private final TeamService teamService;
    private final TeamMapper teamMapper;
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @Autowired
    public AdminController(DeveloperService developerService, DeveloperMapper developerMapper,
                           ReportService reportService, ReportMapper reportMapper, TeamService teamService,
                           TeamMapper teamMapper, TaskService taskService, TaskMapper taskMapper) {
        this.developerService = developerService;
        this.developerMapper = developerMapper;
        this.reportService = reportService;
        this.reportMapper = reportMapper;
        this.teamService = teamService;
        this.teamMapper = teamMapper;
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping("/developer/all")
    public List<DeveloperDTO> getAllDevelopers() {
        return developerService.getAllDevelopers().stream().map(
                developerMapper::convertToDTO).collect(Collectors.toList());
    }

    @DeleteMapping("/developer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Long deleteDeveloperById(@PathVariable Long id) {
        return developerService.deleteDeveloperById(id);
    }

    @GetMapping("/report/all")
    public List<ReportDTO> getAllReports() {
        return reportService.findAll().stream().map(
                reportMapper::convertToDTO).collect(Collectors.toList());
    }

    @DeleteMapping("/report/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Long deleteReportById(@PathVariable Long id) {
        return reportService.deleteById(id);
    }

    @GetMapping("/team/all")
    public List<TeamDTO> getAllTeams() {
        return teamService.getAllTeams().stream().map(
                teamMapper::convertToDTO).collect(Collectors.toList());
    }

    @DeleteMapping("/team/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Long deleteTeamById(@PathVariable Long id) {
        return teamService.deleteTeamById(id);
    }

    @PostMapping("/team/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Long saveTeam(@RequestBody TeamDTO teamDTO) {
        return teamService.saveTeam(teamMapper.convertToEntity(teamDTO));
    }

    @PutMapping("/team/addDev/{devId}/{teamId}")
    public Boolean addDevForTeam(@PathVariable Long devId, @PathVariable Long teamId) {
        return teamService.addDev(devId, teamId);
    }

    @PutMapping("/team/deleteDev/{devId}/{teamId}")
    public Boolean deleteDevForTeam(@PathVariable Long devId, @PathVariable Long teamId) {
        return teamService.deleteDev(devId, teamId);
    }

    @GetMapping("/task/all")
    public List<TaskDTO> getAllTask() {
        return taskService.getAllTasks().stream().map(
                taskMapper::convertToDTO).collect(Collectors.toList());
    }

    @DeleteMapping("/task/{id}")
    public Long deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id);
    }

}
