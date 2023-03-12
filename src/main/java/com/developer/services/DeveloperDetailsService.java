package com.developer.services;

import com.developer.models.Developer;
import com.developer.repositories.DeveloperRepository;
import com.developer.security.DeveloperDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DeveloperDetailsService implements UserDetailsService {
    private final DeveloperRepository developerRepository;

    @Autowired
    public DeveloperDetailsService(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Developer> developer = developerRepository.findByEmail(username);

        if(developer.isEmpty())
            throw new UsernameNotFoundException("Developer not found!");

        return new DeveloperDetails(developer.get());
    }
}
