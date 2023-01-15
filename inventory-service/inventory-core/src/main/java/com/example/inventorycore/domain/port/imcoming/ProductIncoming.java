package com.example.inventorycore.domain.port.imcoming;


import com.example.inventorycore.domain.model.request.CreateProductRequest;
import com.example.inventorycore.domain.model.request.DeductProductRequest;
import com.example.inventorycore.domain.model.response.ProductResponse;

import java.util.List;

public interface ProductIncoming {

    List<ProductResponse> findAll();

    ProductResponse deductInventory(DeductProductRequest request);

    ProductResponse addInventory(CreateProductRequest request);
}
