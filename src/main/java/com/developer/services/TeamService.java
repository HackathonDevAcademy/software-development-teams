package com.developer.services;

import com.developer.models.Team;
import com.developer.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
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
        existingTeam.setContactInfo(team.getContactInfo());
        return teamRepository.save(existingTeam).getId();
    }

}

