package com.guilherme.report_scheduler.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component("cleanOldRecordsCommand")
public class CleanOldRecordsCommand implements JobCommand{

    private static final Logger logger = LoggerFactory.getLogger(CleanOldRecordsCommand.class);


    @Override
    public void execute(){
        logger.info("Executing CleanOldRecordsCommand...");
    }
}
