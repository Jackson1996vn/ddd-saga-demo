package com.example.ordercore.domain.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class OrderResponse {

    private Long id;

    private Long userId;

    private Long productId;

    private Double totalPrice;

    private String orderStatus;

    private Long quantity;

    private Double price;
}
