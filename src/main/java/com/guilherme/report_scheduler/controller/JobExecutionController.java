package com.guilherme.report_scheduler.controller;

import com.guilherme.report_scheduler.model.JobExecution;
import com.guilherme.report_scheduler.model.JobStatus;
import com.guilherme.report_scheduler.repository.JobExecutionRepository;
import com.guilherme.report_scheduler.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/job-executions")
public class JobExecutionController {

    private final JobExecutionRepository jobExecutionRepository;
    private final ReportService reportService;

    public JobExecutionController(JobExecutionRepository jobExecutionRepository, ReportService reportService){
        this.jobExecutionRepository = jobExecutionRepository;
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<List<JobExecution>> getAllExecutions() {
        log.info("Fetching all job executions...");
        return ResponseEntity.ok(jobExecutionRepository.findAll());
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Long>> getExecutionsSummary(){
        log.info("fetching job execution summary...");

        List<JobExecution> allExecutions = jobExecutionRepository.findAll();

        long successCount = allExecutions.stream()
                .filter(e -> e.getStatus() == JobStatus.SUCCESS)
                .count();

        Long failedCount = allExecutions.stream()
                .filter(e -> e.getStatus() == JobStatus.FAILED)
                .count();

        Map<String, Long> summary = Map.of(
                "total", (long) allExecutions.size(),
                "success", successCount,
                "failed", failedCount
        );

        return ResponseEntity.ok(summary);
    }
}
