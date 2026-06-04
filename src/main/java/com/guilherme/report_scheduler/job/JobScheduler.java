package com.guilherme.report_scheduler.job;

import com.guilherme.report_scheduler.command.JobCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobScheduler {

    private static final Logger logger = LoggerFactory.getLogger(JobScheduler.class);


    private final JobCommand generateReportCommand;
    private final JobCommand cleanOldRecordsCommand;

    public JobScheduler(@Qualifier("generateReportCommand") JobCommand generateReportCommand, @Qualifier("cleanOldRecordsCommand") JobCommand cleanOldRecordsCommand){
        this.generateReportCommand = generateReportCommand;
        this.cleanOldRecordsCommand = cleanOldRecordsCommand;
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void runGereteReport(){
        logger.info("Starting Job: GenerateReport");
        generateReportCommand.execute();
    }

    @Scheduled(cron = "0 0 0 * * SUN")
    public void runCleanOldRecords(){
        logger.info("Starting job: cleandOldRecords");
        cleanOldRecordsCommand.execute();
    }
}
