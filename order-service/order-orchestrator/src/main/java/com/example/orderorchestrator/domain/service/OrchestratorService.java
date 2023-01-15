package com.example.orderorchestrator.domain.service;

import com.example.orderorchestrator.domain.*;
import com.example.orderorchestrator.domain.request.InventoryRequest;
import com.example.orderorchestrator.domain.request.OrchestratorRequest;
import com.example.orderorchestrator.domain.request.PaymentRequest;
import com.example.orderorchestrator.domain.response.OrchestratorResponse;
import com.example.orderorchestrator.domain.steps.InventoryStep;
import com.example.orderorchestrator.domain.steps.PaymentStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class OrchestratorService {

    @Autowired
    @Qualifier("payment")
    private WebClient paymentClient;

    @Autowired
    @Qualifier("inventory")
    private WebClient inventoryClient;

    public Mono<OrchestratorResponse> orderProduct(final OrchestratorRequest request) {
        Workflow workflow = this.getOrderWorkflow(request);
        return Flux.fromStream(() -> workflow.getSteps().stream())
                .flatMap(WorkflowStep::process)
                .handle(((aBoolean, synchronousSink) -> {
                    if(aBoolean)
                        synchronousSink.next(true);
                    else
                        synchronousSink.error(new WorkflowException("create order failed!"));
                }))
                .then(Mono.fromCallable(() -> OrchestratorResponse.builder()
                        .orderId(request.getOrderId())
                        .userId(request.getUserId())
                        .orderStatus("ORDER_COMPLETE")
                        .build()))
                .onErrorResume(ex -> this.revertOrder(workflow, request));
    }

    private Mono<OrchestratorResponse> revertOrder(final Workflow workflow, final OrchestratorRequest request){
        return Flux.fromStream(() -> workflow.getSteps().stream())
                .filter(wf -> wf.getStatus().equals(WorkflowStepStatus.COMPLETE))
                .flatMap(WorkflowStep::revert)
                .retry(3)
                .then(Mono.fromCallable(() -> OrchestratorResponse.builder()
                        .orderId(request.getOrderId())
                        .userId(request.getUserId())
                        .orderStatus("ORDER_COMPLETE")
                        .build()));
    }

    private Workflow getOrderWorkflow(OrchestratorRequest request){
        WorkflowStep paymentStep = new PaymentStep(this.paymentClient, this.getPaymentRequestDTO(request));
        WorkflowStep inventoryStep = new InventoryStep(this.inventoryClient, this.getInventoryRequestDTO(request));
        return new OrderWorkflow(List.of(paymentStep, inventoryStep));
    }

    private PaymentRequest getPaymentRequestDTO(OrchestratorRequest request){
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setUserId(request.getUserId());
        paymentRequest.setAmount(request.getPrice() * request.getQuantity());
        paymentRequest.setOrderId(request.getOrderId());
        return paymentRequest;
    }

    private InventoryRequest getInventoryRequestDTO(OrchestratorRequest request){
        InventoryRequest inventoryRequest = new InventoryRequest();
        inventoryRequest.setProductId(request.getProductId());
        inventoryRequest.setQuantity(request.getQuantity());
        return inventoryRequest;
    }
}
