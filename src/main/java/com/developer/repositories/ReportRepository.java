package com.developer.repositories;

import com.developer.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByDeveloperId(Long id);

    @Query(value = "select * from report where date between current_date - :between and current_date",
            nativeQuery = true)
    List<Report> findReportForTheWeek(Integer between);
}
