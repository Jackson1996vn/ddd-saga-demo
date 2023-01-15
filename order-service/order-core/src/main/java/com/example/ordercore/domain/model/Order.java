package com.example.ordercore.domain.model;

import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;


@Table(name = "tbl_order")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    @Id
    @Column("id")
    private Long id;

    @Column("user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column("status")
    private OrderStatus status;

    @Column("product_id")
    private Long productId;

    @Column("quantity")
    private Long quantity;

    @Column("price")
    private Double price;

    @Column("total_price")
    private Double totalPrice;
}
