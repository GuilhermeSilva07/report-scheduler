package com.guilherme.report_scheduler.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// ConcreteCommand — responsável por executar a geração de relatórios
@Component("generateReportCommand")
public class GenerateReportCommand implements JobCommand{

    private static final Logger logger = LoggerFactory.getLogger(GenerateReportCommand.class);

    @Override
    public void execute(){
        logger.info("Executing GenerateReportCommand...");
    }
}
