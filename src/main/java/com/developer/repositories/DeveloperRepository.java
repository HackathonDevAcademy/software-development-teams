package com.developer.repositories;

import com.developer.enums.DevStatus;
import com.developer.models.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    Optional<Developer> findByEmail(String email);
    List<Developer> findByTeamIdAndStatus(Long id, DevStatus devStatus);
    Developer findByResetToken(String resetToken);
}

