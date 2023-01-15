package com.example.inventorycore.infrastructure.adapter;

import com.example.inventorycore.domain.model.Product;
import com.example.inventorycore.domain.model.ProductStatus;
import com.example.inventorycore.domain.port.outgoing.ProductOutgoing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductRepository implements ProductOutgoing {

    private final ProductJPARepository jpaRepository;

    @Override
    public Product createProduct(Product product) {
        return jpaRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Product deductProduct(Long id, Long quantity) {
        Optional<Product> optionalProduct = jpaRepository.findById(id);
        if (optionalProduct.isPresent()
                && ProductStatus.AVAILABLE.equals(optionalProduct.get().getStatus())) {
            Product product = optionalProduct.get();
            product.setQuantity(product.getQuantity() - quantity);
            return jpaRepository.save(product);
        }
        return Product.builder()
                .status(ProductStatus.UNAVAILABLE)
                .build();
    }
}
