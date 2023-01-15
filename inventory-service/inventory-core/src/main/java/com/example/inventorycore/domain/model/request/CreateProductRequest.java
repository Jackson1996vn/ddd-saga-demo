package com.example.inventorycore.domain.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequest {

    private Long quantity;

    private Double price;
}
