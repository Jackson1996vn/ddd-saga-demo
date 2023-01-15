package com.example.paymentcore.domain.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepositRequest {

    private Long userId;

    private Double amount;
}
