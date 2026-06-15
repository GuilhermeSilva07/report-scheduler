package com.guilherme.report_scheduler.controller;

import com.guilherme.report_scheduler.model.Order;
import com.guilherme.report_scheduler.model.OrderStatus;
import com.guilherme.report_scheduler.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Order>> getPendingOrders(){
        log.info("fetching pending orders older than 24...");

        LocalDateTime twentyFourHoursAgo = LocalDateTime.now().minusHours(24);
        List<Order> pendingOrders = orderRepository
                .findByStatusAndCreatedAtBefore(OrderStatus.PENDING, twentyFourHoursAgo);

        return ResponseEntity.ok(pendingOrders);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(){
        log.info("fetching all orders..");
        return ResponseEntity.ok(orderRepository.findAll());
    }
}
