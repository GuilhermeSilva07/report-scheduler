package com.guilherme.report_scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReportSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportSchedulerApplication.class, args);
	}

}
