package com.example.ordercore.domain.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderRequest {

    private Long userId;

    private Long productId;

    private Long quantity;

    private Double price;
}
