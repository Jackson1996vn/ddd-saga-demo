package com.example.inventorycore.domain.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeductProductRequest {

    private Long productId;

    private Long quantity;
}
