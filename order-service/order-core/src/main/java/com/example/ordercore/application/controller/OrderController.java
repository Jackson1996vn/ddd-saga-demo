package com.example.ordercore.application.controller;

import com.example.ordercore.domain.model.request.CreateOrderRequest;
import com.example.ordercore.domain.model.response.OrderResponse;
import com.example.ordercore.domain.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Mono<OrderResponse> createOrder(@RequestBody @Valid
                                                     Mono<CreateOrderRequest> request) {
        return request.flatMap(this.orderService::createOrder);
    }

//    @GetMapping("/{id}")
//    public Mono<OrderResponse> getOrderId(@PathVariable Long id) {
//        return this.orderService.findOrderById(id);
//    }
}
