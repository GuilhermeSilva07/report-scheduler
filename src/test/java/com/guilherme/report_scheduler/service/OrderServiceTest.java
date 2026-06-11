package com.guilherme.report_scheduler.service;

import com.guilherme.report_scheduler.model.Order;
import com.guilherme.report_scheduler.model.OrderStatus;
import com.guilherme.report_scheduler.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order pendingOrder;

    @BeforeEach
    void setUp(){
        pendingOrder = Order.builder()
                .id(1L)
                .customerName("João Silva")
                .amount(new BigDecimal("150.00"))
                .status(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now().minusHours(25))
                .build();
    }

    @Test
    void shouldFindPendingOrdersOlderThan24Hours(){
        when(orderRepository.findByStatusAndCreatedAtBefore(eq(OrderStatus.PENDING), any()))
                .thenReturn(List.of(pendingOrder));

        orderService.generatePendingOrdersReport();

        verify(orderRepository).findByStatusAndCreatedAtBefore(eq(OrderStatus.PENDING), any());
    }

    @Test
    void shouldReturnEmptyWhenNoPendingOrders() {
        when(orderRepository.findByStatusAndCreatedAtBefore(eq(OrderStatus.PENDING), any()))
                .thenReturn(List.of());

        orderService.generatePendingOrdersReport();

        verify(orderRepository).findByStatusAndCreatedAtBefore(eq(OrderStatus.PENDING), any());
    }
}
