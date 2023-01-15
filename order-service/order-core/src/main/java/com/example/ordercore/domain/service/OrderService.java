package com.example.ordercore.domain.service;

import com.example.ordercore.domain.model.Order;
import com.example.ordercore.domain.model.OrderStatus;
import com.example.ordercore.domain.model.request.CreateOrderRequest;
import com.example.ordercore.domain.model.request.OrchestratorRequest;
import com.example.ordercore.domain.model.response.OrchestratorResponse;
import com.example.ordercore.domain.model.response.OrderResponse;
import com.example.ordercore.domain.port.imcoming.OrderIncoming;
import com.example.ordercore.infrastructure.apdater.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class OrderService implements OrderIncoming {

    private final OrderRepository repository;

    private final Sinks.Many<OrchestratorRequest> sink;

    @Override
    public Mono<OrderResponse> createOrder(CreateOrderRequest request) {
        Order order = this.mappingToOrder(request);
        return this.repository.createOrder(order)
                .doOnNext(this::emitEvent)
                .map(this::mappingToOrderResponse);
    }

    @Override
    public Mono<OrderResponse> findOrderById(Long id) {
        return this.repository.findById(id).map(e -> mappingToOrderResponse(e));
    }

    @Override
    public Mono<Void> updateOrder(final OrchestratorResponse orchestratorResponse) {
        return this.repository.findById(orchestratorResponse.getOrderId())
                .doOnNext(p -> {
                    System.out.println(p);
                    p.setStatus(OrderStatus.valueOf(orchestratorResponse.getOrderStatus()));
                })
                .flatMap(this.repository::updateOrder)
                .then();
    }

    private void emitEvent(Order order){
        OrderResponse orderResponse = mappingToOrderResponse(order);
        this.sink.tryEmitNext(this.getOrchestratorRequest(orderResponse));
    }

    private OrderResponse mappingToOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .productId(order.getProductId())
                .orderStatus(order.getStatus().name())
                .price(order.getPrice())
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .build();
    }

    private Order mappingToOrder(CreateOrderRequest request) {
        return Order.builder()
                .userId(request.getUserId())
                .status(OrderStatus.ORDER_CREATED)
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .totalPrice(request.getPrice() * request.getQuantity())
                .build();
    }

    private OrchestratorRequest getOrchestratorRequest(OrderResponse orderResponse) {
        return OrchestratorRequest.builder()
                .orderId(orderResponse.getId())
                .price(orderResponse.getTotalPrice())
                .quantity(orderResponse.getQuantity())
                .productId(orderResponse.getProductId())
                .userId(orderResponse.getUserId())
                .build();
    }
}
