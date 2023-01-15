package com.example.orderorchestrator.domain.steps;

import com.example.orderorchestrator.domain.WorkflowStep;
import com.example.orderorchestrator.domain.WorkflowStepStatus;
import com.example.orderorchestrator.domain.request.PaymentRequest;
import com.example.orderorchestrator.domain.response.PaymentResponse;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class PaymentStep implements WorkflowStep {

    private final WebClient webClient;

    private final PaymentRequest request;

    private WorkflowStepStatus stepStatus = WorkflowStepStatus.PENDING;

    public PaymentStep(WebClient webClient, PaymentRequest request) {
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
                .uri("payment/users/withdraw")
                .body(BodyInserters.fromValue(this.request))
                .retrieve()
                .bodyToMono(PaymentResponse.class)
                .map(r -> r.getBalance() >= 0)
                .doOnNext(b -> this.stepStatus = b ? WorkflowStepStatus.COMPLETE : WorkflowStepStatus.FAILED);
    }

    @Override
    public Mono<Boolean> revert() {
        return null;
    }
}
