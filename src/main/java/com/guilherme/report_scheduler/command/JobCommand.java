package com.guilherme.report_scheduler.command;

// Command Pattern — contrato base para todos os jobs do sistema
public interface JobCommand {

    void execute();
}
