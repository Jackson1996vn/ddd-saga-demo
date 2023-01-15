package com.example.paymentcore.domain.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WithdrawRequest {

    private Long userId;

    private Double amount;
}
