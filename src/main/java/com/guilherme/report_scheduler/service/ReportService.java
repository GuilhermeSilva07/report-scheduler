package com.guilherme.report_scheduler.service;

import com.guilherme.report_scheduler.model.JobExecution;
import com.guilherme.report_scheduler.model.JobStatus;
import com.guilherme.report_scheduler.repository.JobExecutionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class ReportService {

    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);

    private final JobExecutionRepository jobExecutionRepository;

    public ReportService(JobExecutionRepository jobExecutionRepository) {
        this.jobExecutionRepository = jobExecutionRepository;
    }

    public void generateReport() {
        logger.info("Generating report...");

        try {

            JobExecution execution = JobExecution.builder()
                    .JobName("GenerateReport")
                    .status(JobStatus.SUCCESS)
                    .executedAt(LocalDateTime.now())
                    .build();

            jobExecutionRepository.save(execution);
            logger.info("Report generated successfully.");

        } catch (Exception e) {
            logger.error("Error generating report: {}", e.getMessage());

            JobExecution execution = JobExecution.builder()
                    .JobName("GenerateReport")
                    .status(JobStatus.FAILED)
                    .executedAt(LocalDateTime.now())
                    .errorMessage(e.getMessage())
                    .build();

            jobExecutionRepository.save(execution);
        }
    }
}