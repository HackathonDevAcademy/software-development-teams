package com.developer.services;

import com.developer.models.Developer;
import com.developer.models.Team;
import com.developer.repositories.DeveloperRepository;
import com.developer.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final DeveloperRepository developerRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository, DeveloperRepository developerRepository) {
        this.teamRepository = teamRepository;
        this.developerRepository = developerRepository;
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team getTeamById(Long id) {
        return teamRepository.findById(id).orElse(null);
    }

    public Long saveTeam(Team team) {
        return teamRepository.save(team).getId();
    }

    public Long deleteTeamById(Long id) {
        teamRepository.deleteById(id);
        return id;
    }

    public Long updateTeam(Long id, Team team) {
        Team existingTeam = teamRepository.findById(id).orElse(null);
        if (existingTeam == null)
            return null;

        existingTeam.setName(team.getName());
        existingTeam.setDescription(team.getDescription());
        existingTeam.setDevelopers(team.getDevelopers());
        return teamRepository.save(existingTeam).getId();
    }

    public Team findByDeveloperId(Long id) {
        return teamRepository.findByDeveloperId(id);
    }

    public Boolean addDev(Long devId, Long teamId) {
        Developer developer = developerRepository.findById(devId).orElse(null);
        Team team = teamRepository.findById(teamId).orElse(null);
        if (developer == null || team == null)
            return false;

        team.getDevelopers().add(developer);
        return true;
    }

    public Boolean deleteDev(Long devId, Long teamId) {
        Developer developer = developerRepository.findById(devId).orElse(null);
        Team team = teamRepository.findById(teamId).orElse(null);
        if (!team.getDevelopers().isEmpty())
            return team.getDevelopers().remove(developer);

        return false;
    }

}

