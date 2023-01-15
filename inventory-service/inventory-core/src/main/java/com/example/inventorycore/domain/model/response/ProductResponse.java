package com.example.inventorycore.domain.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductResponse {

    private Long id;

    private String productStatus;

    private Long quantity;

    private Double price;
}
