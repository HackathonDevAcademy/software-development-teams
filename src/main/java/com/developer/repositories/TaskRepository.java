package com.developer.repositories;

import com.developer.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query(value = "SELECT * FROM task WHERE start_date >= CAST(:startDate AS timestamp) AND end_date <= CAST(:endDate AS timestamp) " +
            "order by start_date", nativeQuery = true)
    List<Task> createReport(String startDate, String endDate);

    @Query(value = "SELECT * FROM task WHERE developer_id = :id AND start_date >= CAST(:startDate AS timestamp) " +
            "AND end_date <= CAST(:endDate AS timestamp) order by start_date", nativeQuery = true)
    List<Task> createReportByDevId(Long id, String startDate, String endDate);
}
