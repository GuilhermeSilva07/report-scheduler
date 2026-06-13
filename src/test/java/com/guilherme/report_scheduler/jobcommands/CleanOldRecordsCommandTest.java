package com.guilherme.report_scheduler.jobcommands;

import com.guilherme.report_scheduler.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CleanOldRecordsCommandTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private CleanOldRecordsCommand cleanOldRecordsCommand;

    @Test
    void shouldCallCleanCancelledOrders() {
        cleanOldRecordsCommand.execute();

        verify(orderService).cleanCancelledOrders();
    }
}