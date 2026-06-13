package com.guilherme.report_scheduler.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


// Invoker — agenda e delega a execução dos jobs via @Scheduled
@Slf4j
@Component
public class JobScheduler {

    private final JobProcessor jobProcessor;

    public JobScheduler(JobProcessor jobProcessor) {
        this.jobProcessor = jobProcessor;
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void runGereteReport(){
        jobProcessor.execute("generateReportCommand");
    }

    @Scheduled(cron = "0 0 0 * * SUN")
    public void runCleanOldRecords(){
        jobProcessor.execute("cleanOldRecordsCommand");
    }
}
