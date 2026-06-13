package com.guilherme.report_scheduler.job;

import com.guilherme.report_scheduler.command.JobCommand;
import com.guilherme.report_scheduler.model.JobExecution;
import com.guilherme.report_scheduler.model.JobStatus;
import com.guilherme.report_scheduler.repository.JobExecutionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobProcessorTest {

    @Mock
    private JobExecutionRepository jobExecutionRepository;

    @Mock
    private JobCommand generateReportCommand;

    private JobProcessor jobProcessor;

    @BeforeEach
    void setUp() {
        Map<String, JobCommand> jobsMap = Map.of("generateReportCommand", generateReportCommand);
        jobProcessor = new JobProcessor(jobsMap, jobExecutionRepository);
    }

    @Test
    void shouldExecuteJobSuccessfully() {
        jobProcessor.execute("generateReportCommand");

        verify(generateReportCommand).execute();
        ArgumentCaptor<JobExecution> captor = ArgumentCaptor.forClass(JobExecution.class);
        verify(jobExecutionRepository).save(captor.capture());
        assertEquals(JobStatus.SUCCESS, captor.getValue().getStatus());
    }

    @Test
    void shouldSaveFailedExecutionWhenJobThrowsException() {
        doThrow(new RuntimeException("Simulated error")).when(generateReportCommand).execute();

        jobProcessor.execute("generateReportCommand");

        ArgumentCaptor<JobExecution> captor = ArgumentCaptor.forClass(JobExecution.class);
        verify(jobExecutionRepository).save(captor.capture());
        assertEquals(JobStatus.FAILED, captor.getValue().getStatus());
        assertEquals("Simulated error", captor.getValue().getErrorMessage());
    }

    @Test
    void shouldNotExecuteWhenJobNotFoundInMap() {
        jobProcessor.execute("jobInexistente");

        verify(generateReportCommand, never()).execute();
        verify(jobExecutionRepository, never()).save(any());
    }
}