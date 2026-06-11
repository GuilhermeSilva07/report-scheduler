package com.guilherme.report_scheduler.service;

import com.guilherme.report_scheduler.model.JobExecution;
import com.guilherme.report_scheduler.model.JobStatus;
import com.guilherme.report_scheduler.model.Order;
import com.guilherme.report_scheduler.model.OrderStatus;
import com.guilherme.report_scheduler.repository.JobExecutionRepository;
import com.guilherme.report_scheduler.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final JobExecutionRepository jobExecutionRepository;

    public OrderService(OrderRepository orderRepository, JobExecutionRepository jobExecutionRepository) {
        this.orderRepository = orderRepository;
        this.jobExecutionRepository = jobExecutionRepository;
    }

    public void generatePendingOrdersReport() {
        log.info("Generating pending orders report...");

        LocalDateTime twentyFourHoursAgo = LocalDateTime.now().minusHours(24);

        List<Order> pendingOrders = orderRepository
                .findByStatusAndCreatedAtBefore(OrderStatus.PENDING, twentyFourHoursAgo);

        log.info("Found {} pending orders older than 24h.", pendingOrders.size());

        pendingOrders.forEach(order ->
                log.warn("ALERT - Order ID: {}, Customer: {}, Amount: {}, Created at: {}",
                        order.getId(),
                        order.getCustomerName(),
                        order.getAmount(),
                        order.getCreatedAt())
        );
    }

    public void cleanCancelledOrders() {
        log.info("Cleaning cancelled orders...");

        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);

        List<Order> cancelledOrders = orderRepository
                .findByStatusAndCreatedAtBefore(OrderStatus.CANCELLED, thirtyDaysAgo);

        log.info("Found {} cancelled orders older than 30 days.", cancelledOrders.size());

        orderRepository.deleteAll(cancelledOrders);
    }
}