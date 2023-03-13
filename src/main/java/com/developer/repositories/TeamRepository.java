package com.developer.repositories;

import com.developer.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    @Query(value = "SELECT team.id, team.name, team.description FROM team JOIN developer " +
            "ON(team.id = developer.team_id) WHERE developer.id = :id", nativeQuery = true)
    Team findByDeveloperId(Long id);
}
