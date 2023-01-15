package com.example.ordercore.domain.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
public class OrchestratorRequest {

    private Long userId;

    private Long orderId;

    private Long quantity;

    private Long productId;

    private Double price;
}
