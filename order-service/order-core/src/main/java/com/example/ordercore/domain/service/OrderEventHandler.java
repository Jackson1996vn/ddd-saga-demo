package com.example.ordercore.domain.service;

import com.example.ordercore.domain.model.request.OrchestratorRequest;
import com.example.ordercore.domain.model.response.OrchestratorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
public class OrderEventHandler {

    @Autowired
    private Flux<OrchestratorRequest> flux;

    @Autowired
    private OrderService orderService;

    @Bean
    public Supplier<Flux<OrchestratorRequest>> supplier(){
        return () -> flux;
    };

    @Bean
    public Consumer<Flux<OrchestratorResponse>> consumer(){
        return f -> f
                .doOnNext(c -> System.out.println("Consuming :: " + c))
                .subscribe(this.orderService::updateOrder);
    };
}
