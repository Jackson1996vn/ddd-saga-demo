package com.example.orderorchestrator.domain.steps;

import com.example.orderorchestrator.domain.WorkflowStep;
import com.example.orderorchestrator.domain.WorkflowStepStatus;
import com.example.orderorchestrator.domain.request.InventoryRequest;
import com.example.orderorchestrator.domain.response.InventoryResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

public class InventoryStep implements WorkflowStep {

    private final WebClient webClient;

    private final InventoryRequest request;

    private WorkflowStepStatus stepStatus = WorkflowStepStatus.PENDING;

    public InventoryStep(WebClient webClient, InventoryRequest request) {
        this.webClient = webClient;
        this.request = request;
    }

    @Override
    public WorkflowStepStatus getStatus() {
        return this.stepStatus;
    }

    @Override
    public Mono<Boolean> process() {
        return this.webClient
                .post()
                .uri("inventory/deduct")
                .body(BodyInserters.fromValue(this.request))
                .retrieve()
                .bodyToMono(InventoryResponse.class)
                .map(r -> r.getProductStatus().equals("AVAILABLE"))
                .doOnNext(b -> this.stepStatus = b ? WorkflowStepStatus.COMPLETE : WorkflowStepStatus.FAILED);
    }

    @Override
    public Mono<Boolean> revert() {
        return null;
    }
}
