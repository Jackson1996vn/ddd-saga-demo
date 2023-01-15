package com.example.orderorchestrator.domain.request;

import lombok.Data;

@Data
public class OrchestratorRequest {

    private Long userId;

    private Long orderId;

    private Long quantity;

    private Long productId;

    private Double price;
}
