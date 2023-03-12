package com.developer.services;

import com.developer.enums.Role;
import com.developer.models.Developer;
import com.developer.repositories.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeveloperService {
    private final DeveloperRepository developerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DeveloperService(DeveloperRepository developerRepository, PasswordEncoder passwordEncoder) {
        this.developerRepository = developerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Developer> getAllDevelopers() {
        return developerRepository.findAll();
    }

    public Developer getDeveloperById(Long id) {
        return developerRepository.findById(id).orElse(null);
    }

    public Long saveDeveloper(Developer developer) {
        developer.setPassword(passwordEncoder.encode(developer.getPassword()));
        developer.setRole(Role.ROLE_USER);
        return developerRepository.save(developer).getId();
    }

    public Long deleteDeveloperById(Long id) {
        developerRepository.deleteById(id);
        return id;
    }

    public Long updateDeveloper(Long id, Developer developer) {
        Developer existingDeveloper = developerRepository.findById(id).orElse(null);
        if (existingDeveloper == null)
            return null;

        existingDeveloper.setEmail(developer.getEmail());
        existingDeveloper.setPassword(developer.getPassword());
        existingDeveloper.setRole(developer.getRole());
        existingDeveloper.setFullName(developer.getFullName());
        existingDeveloper.setPosition(developer.getPosition());
        existingDeveloper.setTeams(developer.getTeams());
//        existingDeveloper.setProjects(developer.getProjects());
        existingDeveloper.setTasks(developer.getTasks());
        return developerRepository.save(existingDeveloper).getId();
    }

    public Optional<Developer> findByEmail(String email) {
        return developerRepository.findByEmail(email);
    }

}
