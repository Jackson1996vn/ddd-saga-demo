package com.example.ordercore.domain.port.imcoming;

import com.example.ordercore.domain.model.request.CreateOrderRequest;
import com.example.ordercore.domain.model.response.OrchestratorResponse;
import com.example.ordercore.domain.model.response.OrderResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface OrderIncoming {

    Mono<OrderResponse> createOrder(CreateOrderRequest request);

    Mono<OrderResponse> findOrderById(Long id);

    Mono<Void> updateOrder(final OrchestratorResponse orchestratorResponse);
}
