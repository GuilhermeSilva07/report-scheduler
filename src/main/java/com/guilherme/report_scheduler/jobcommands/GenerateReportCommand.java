package com.guilherme.report_scheduler.jobcommands;

import com.guilherme.report_scheduler.command.JobCommand;
import com.guilherme.report_scheduler.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

// ConcreteCommand — responsável por executar a geração de relatórios
@Slf4j
@Component("generateReportCommand")
public class GenerateReportCommand implements JobCommand {

    private final OrderService orderService;

    public GenerateReportCommand(OrderService orderService){
        this.orderService = orderService;
    }

    @Override
    public void execute(){
        orderService.generatePendingOrdersReport();
    }
}
