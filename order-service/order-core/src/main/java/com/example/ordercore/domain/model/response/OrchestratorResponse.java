package com.example.ordercore.domain.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class OrchestratorResponse {

    private Long userId;

    private Long orderId;

    private String orderStatus;
}
