package com.example.ordercore.infrastructure.apdater;

import com.example.ordercore.domain.model.Order;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderJPARepository extends R2dbcRepository<Order, Long> {
}
