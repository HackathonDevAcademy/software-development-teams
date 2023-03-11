package com.developer.services;

import com.developer.models.Project;
import com.developer.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    public Long saveProject(Project project) {
        return projectRepository.save(project).getId();
    }

    public Long updateProject(Long id, Project updatedProject) {
        Project project = projectRepository.findById(id).orElse(null);
        if (project == null)
            return null;

        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        project.setStartDate(updatedProject.getStartDate());
        project.setEndDate(updatedProject.getEndDate());
        project.setTeam(updatedProject.getTeam());
//        project.setDevelopers(updatedProject.getDevelopers());

        return projectRepository.save(project).getId();
    }

    public Long deleteProject(Long id) {
        projectRepository.deleteById(id);
        return id;
    }
}
