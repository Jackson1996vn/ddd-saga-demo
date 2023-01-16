package com.example.inventorycore.application.controller;

import com.example.inventorycore.domain.model.request.CreateProductRequest;
import com.example.inventorycore.domain.model.request.DeductProductRequest;
import com.example.inventorycore.domain.model.response.ProductResponse;
import com.example.inventorycore.domain.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/inventory")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private final ProductService productService;

    @PostMapping("/deduct")
    public ResponseEntity<ProductResponse> deduct(@RequestBody final DeductProductRequest requestDTO){
        return ResponseEntity.ok(productService.deductInventory(requestDTO));
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody final CreateProductRequest requestDTO) throws JsonProcessingException {
        return ResponseEntity.ok(productService.addInventory(requestDTO));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

}
