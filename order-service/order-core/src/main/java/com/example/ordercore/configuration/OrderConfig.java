package com.example.ordercore.configuration;

import com.example.ordercore.domain.model.request.OrchestratorRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class OrderConfig {

    @Bean
    public Sinks.Many<OrchestratorRequest> sink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Flux<OrchestratorRequest> flux(Sinks.Many<OrchestratorRequest> sink) {
        return sink.asFlux();
    }
}
