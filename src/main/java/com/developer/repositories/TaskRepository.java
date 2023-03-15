package com.developer.repositories;

import com.developer.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
//    List<Report> findByDeveloperId(Long id);
//
//    @Query(value = "select * from report where date between current_date - :between and current_date",
//            nativeQuery = true)
//    List<Report> findReportForTheWeek(Integer between);

    @Query(value = "SELECT * FROM task WHERE start_date >= CAST(:startDate AS timestamp) AND end_date <= CAST(:endDate AS timestamp)", nativeQuery = true)
    List<Task> createReport(String startDate, String endDate);
}
