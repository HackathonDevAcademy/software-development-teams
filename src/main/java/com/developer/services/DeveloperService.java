package com.developer.services;

import com.developer.models.Developer;
import com.developer.repositories.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeveloperService {
    private final DeveloperRepository developerRepository;

    @Autowired
    public DeveloperService(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    public List<Developer> getAllDevelopers() {
        return developerRepository.findAll();
    }

    public Developer getDeveloperById(Long id) {
        return developerRepository.findById(id).orElse(null);
    }

    public Developer saveDeveloper(Developer developer) {
        return developerRepository.save(developer);
    }

    public void deleteDeveloperById(Long id) {
        developerRepository.deleteById(id);
    }

    public Long updateDeveloper(Long id, Developer developer) {
        Developer existingDeveloper = developerRepository.findById(id).orElse(null);
        if (existingDeveloper == null)
            return null;

        existingDeveloper.setEmail(developer.getEmail());
        existingDeveloper.setPassword(developer.getPassword());
        existingDeveloper.setRole(developer.getRole());
        existingDeveloper.setFirstName(developer.getFirstName());
        existingDeveloper.setLastName(developer.getLastName());
        existingDeveloper.setPosition(developer.getPosition());
        existingDeveloper.setTeams(developer.getTeams());
        existingDeveloper.setProjects(developer.getProjects());
        existingDeveloper.setTasks(developer.getTasks());
        return developerRepository.save(existingDeveloper).getId();
    }
}
