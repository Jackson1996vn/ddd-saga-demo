package com.example.ordercore.domain.port.outgoing;

import com.example.ordercore.domain.model.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface OrderOutgoing {

    Mono<Order> createOrder(Order order);

    Mono<Order> findById(Long id);

    Mono<Void> updateOrder(Order order);
}
