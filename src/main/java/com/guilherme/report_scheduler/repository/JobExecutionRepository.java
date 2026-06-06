package com.guilherme.report_scheduler.repository;

import com.guilherme.report_scheduler.model.JobExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobExecutionRepository extends JpaRepository<JobExecution, Long> {

}
