package com.developer.mapper;

import com.developer.dto.DeveloperDTO;
import com.developer.dto.DeveloperDTOForAdmin;
import com.developer.models.Developer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeveloperDTOForAdminMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public DeveloperDTOForAdminMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DeveloperDTOForAdmin convertToDTO(Developer developer) {
        return modelMapper.map(developer, DeveloperDTOForAdmin.class);
    }

    public Developer convertToEntity(DeveloperDTOForAdmin developerDTO) {
        return modelMapper.map(developerDTO, Developer.class);
    }
}
