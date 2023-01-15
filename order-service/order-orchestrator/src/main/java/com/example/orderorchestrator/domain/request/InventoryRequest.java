package com.example.orderorchestrator.domain.request;

import lombok.Data;

@Data
public class InventoryRequest {

    private Long productId;

    private Long quantity;
}
