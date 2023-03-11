package com.developer.mapper;

import com.developer.dto.TeamDTO;
import com.developer.models.Team;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeamMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public TeamMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TeamDTO convertToDTO(Team team) {
        return modelMapper.map(team, TeamDTO.class);
    }

    public Team convertToEntity(TeamDTO teamDTO) {
        return modelMapper.map(teamDTO, Team.class);
    }
}
