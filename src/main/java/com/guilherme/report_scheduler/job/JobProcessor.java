package com.guilherme.report_scheduler.job;

import com.guilherme.report_scheduler.command.JobCommand;
import com.guilherme.report_scheduler.model.JobExecution;
import com.guilherme.report_scheduler.model.JobStatus;
import com.guilherme.report_scheduler.repository.JobExecutionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Component
public class JobProcessor {

    private final Map<String, JobCommand> jobsMap;
    private final JobExecutionRepository jobExecutionRepository;

    public JobProcessor(Map<String, JobCommand> jobsMap, JobExecutionRepository jobExecutionRepository){
        this.jobsMap = jobsMap;
        this.jobExecutionRepository = jobExecutionRepository;
    }

    public void execute(String jobName){
        log.info("Starting job: {}", jobName);

        JobCommand command = jobsMap.get(jobName);

        if (command == null){
            log.error("Job not found in jobsMap: {}", jobName);
            return;
        }

        try{
            command.execute();
            saveExecution(jobName, JobStatus.SUCCESS, null);
            log.info("Job {} completed successfully.", jobName);
        } catch (Exception e){
            log.error("Job {} failed: {}", jobName, e.getMessage());
            saveExecution(jobName, JobStatus.FAILED, e.getMessage());
        }
    }

    private void saveExecution(String jobName, JobStatus status, String errorMessage){
        JobExecution execution = JobExecution.builder()
                .JobName(jobName)
                .status(status)
                .executedAt(LocalDateTime.now())
                .errorMessage(errorMessage)
                .build();

        jobExecutionRepository.save(execution);
    }
}
