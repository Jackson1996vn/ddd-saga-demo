package com.example.inventorycore.infrastructure.adapter;

import com.example.inventorycore.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJPARepository extends JpaRepository<Product, Long> {
}
