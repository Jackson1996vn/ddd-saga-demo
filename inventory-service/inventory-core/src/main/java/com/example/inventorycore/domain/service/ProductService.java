package com.example.inventorycore.domain.service;

import com.example.inventorycore.domain.model.Product;
import com.example.inventorycore.domain.model.ProductStatus;
import com.example.inventorycore.domain.model.request.CreateProductRequest;
import com.example.inventorycore.domain.model.request.DeductProductRequest;
import com.example.inventorycore.domain.model.response.ProductResponse;
import com.example.inventorycore.domain.port.imcoming.ProductIncoming;
import com.example.inventorycore.infrastructure.adapter.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductIncoming {

    private final ProductRepository repository;

    private final KafkaService kafkaService;

    private final ObjectMapper objectMapper;

    @Override
    public List<ProductResponse> findAll() {
        return repository.findAll().stream()
                .map(this::mappingToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse deductInventory(DeductProductRequest request) {
        Product product = repository.deductProduct(request.getProductId(), request.getQuantity());
        return mappingToResponse(product);
    }

    @Override
    public String addInventory(CreateProductRequest request) throws JsonProcessingException {
        Product product = this.mappingToProduct(request);
        kafkaService.send(objectMapper.writeValueAsString(product));
        return "Success";
    }

    private ProductResponse mappingToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .productStatus(product.getStatus().name())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }

    private Product mappingToProduct(CreateProductRequest request) {
        return Product.builder()
                .status(request.getQuantity() > 0 ? ProductStatus.AVAILABLE : ProductStatus.UNAVAILABLE)
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .build();
    }
}
