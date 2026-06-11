package com.guilherme.report_scheduler.service;

import com.guilherme.report_scheduler.model.JobExecution;
import com.guilherme.report_scheduler.model.JobStatus;
import com.guilherme.report_scheduler.repository.JobExecutionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;

@Slf4j
@Service
public class ReportService {

    private final JobExecutionRepository jobExecutionRepository;

    public ReportService(JobExecutionRepository jobExecutionRepository) {
        this.jobExecutionRepository = jobExecutionRepository;
    }

    public void printExecutionSummary() {
        log.info("Generating job execution summary...");


        List<JobExecution> allExecutions = jobExecutionRepository.findAll();

        Long successCount = allExecutions.stream()
                .filter(e -> e.getStatus() == JobStatus.SUCCESS)
                .count();

        Long failedCount = allExecutions.stream()
                .filter(e -> e.getStatus() == JobStatus.FAILED)
                .count();

        log.info("Job Execution Summary — Total: {}, Success: {}, Failed: {}",
                allExecutions.size(), successCount, failedCount);
    }
}