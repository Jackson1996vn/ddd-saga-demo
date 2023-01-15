package com.example.orderorchestrator.configuration;

import com.example.orderorchestrator.domain.request.OrchestratorRequest;
import com.example.orderorchestrator.domain.response.OrchestratorResponse;
import com.example.orderorchestrator.domain.service.OrchestratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Configuration
public class OrchestratorConfig {

    @Autowired
    private OrchestratorService orchestratorService;

    @Bean
    public Function<Flux<OrchestratorRequest>, Flux<OrchestratorResponse>> processor() {
        return flux -> flux
                .flatMap(dto -> this.orchestratorService.orderProduct(dto))
                .doOnNext(dto -> System.out.println("Status : " + dto.getOrderStatus()));
    }
}
