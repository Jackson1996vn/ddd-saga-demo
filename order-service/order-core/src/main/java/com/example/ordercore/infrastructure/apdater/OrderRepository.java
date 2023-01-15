package com.example.ordercore.infrastructure.apdater;

import com.example.ordercore.domain.model.Order;
import com.example.ordercore.domain.port.outgoing.OrderOutgoing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderRepository implements OrderOutgoing {

    private final OrderJPARepository jpaRepository;

    @Override
    public Mono<Order> createOrder(Order order) {
        order.setId(new Date().getTime());
        return jpaRepository.save(order);
    }

    @Override
    public Mono<Order> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Mono<Void> updateOrder(Order order) {
        return this.jpaRepository.save(order)
                .then();
    }
}
