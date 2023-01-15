package com.example.orderorchestrator.domain.response;

import lombok.Data;

@Data
public class InventoryResponse {

    private Long id;

    private Long quantity;

    private Double price;

    private String productStatus;
}
