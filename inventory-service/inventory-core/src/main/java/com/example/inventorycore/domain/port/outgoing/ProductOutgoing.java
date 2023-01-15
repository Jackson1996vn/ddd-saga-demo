package com.example.inventorycore.domain.port.outgoing;

import com.example.inventorycore.domain.model.Product;

import java.util.List;

public interface ProductOutgoing {

    Product createProduct(Product product);

    List<Product> findAll();

    Product deductProduct(Long id, Long quantity);
}
