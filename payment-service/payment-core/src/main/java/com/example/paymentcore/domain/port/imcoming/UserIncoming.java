package com.example.paymentcore.domain.port.imcoming;


import com.example.paymentcore.domain.model.request.CreateUserRequest;
import com.example.paymentcore.domain.model.request.DepositRequest;
import com.example.paymentcore.domain.model.request.WithdrawRequest;
import com.example.paymentcore.domain.model.response.UserResponse;

import java.util.List;

public interface UserIncoming {

    List<UserResponse> findAll();

    UserResponse createUser(CreateUserRequest request);

    UserResponse deposit(DepositRequest request);

    UserResponse withdraw(WithdrawRequest request);
}
