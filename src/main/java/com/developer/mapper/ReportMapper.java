package com.developer.mapper;

import com.developer.dto.ReportDTO;
import com.developer.models.Report;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public ReportMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ReportDTO convertToDTO(Report report) {
        return modelMapper.map(report, ReportDTO.class);
    }

    public Report convertToEntity(ReportDTO reportDTO) {
        return modelMapper.map(reportDTO, Report.class);
    }
}
