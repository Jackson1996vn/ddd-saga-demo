package com.example.orderorchestrator.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrchestratorResponse {

    private Long userId;

    private Long orderId;

    private String orderStatus;
}
