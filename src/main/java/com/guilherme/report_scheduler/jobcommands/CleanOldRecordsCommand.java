package com.guilherme.report_scheduler.jobcommands;

import com.guilherme.report_scheduler.command.JobCommand;
import com.guilherme.report_scheduler.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

// ConcreteCommand — responsável por limpar registros antigos do banco
@Slf4j
@Component("cleanOldRecordsCommand")
public class CleanOldRecordsCommand implements JobCommand {

    private final OrderService orderService;

    public CleanOldRecordsCommand(OrderService orderService){this.orderService = orderService;}

    @Override
    public void execute(){
        orderService.cleanCancelledOrders();
    }
}
