package com.example.paymentcore.domain.service;

import com.example.paymentcore.domain.model.User;
import com.example.paymentcore.domain.model.request.CreateUserRequest;
import com.example.paymentcore.domain.model.request.DepositRequest;
import com.example.paymentcore.domain.model.request.WithdrawRequest;
import com.example.paymentcore.domain.model.response.UserResponse;
import com.example.paymentcore.domain.port.imcoming.UserIncoming;
import com.example.paymentcore.infrastructure.adapter.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserIncoming {

    private final UserRepository repository;

    @Override
    public List<UserResponse> findAll() {
        return repository.findAll().stream()
                .map(this::mappingToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        UserResponse userResponse = mappingToResponse(repository.createUser(mappingToUser(request)));
        return userResponse;
    }

    @Override
    public UserResponse deposit(DepositRequest request) {
        UserResponse userResponse = mappingToResponse(repository.deposit(mappingToUser(request)));
        return userResponse;
    }

    @Override
    public UserResponse withdraw(WithdrawRequest request) {
        UserResponse userResponse = mappingToResponse(repository.withdraw(mappingToUser(request)));
        return userResponse;
    }

    private UserResponse mappingToResponse(User user) {
        if (user == null) return null;
        return UserResponse.builder()
                .id(user.getId())
                .balance(user.getBalance())
                .build();
    }

    private User mappingToUser(CreateUserRequest request) {
        return User.builder()
                .balance(request.getBalance())
                .build();
    }

    private User mappingToUser(DepositRequest request) {
        return User.builder()
                .id(request.getUserId())
                .balance(request.getAmount())
                .build();
    }

    private User mappingToUser(WithdrawRequest request) {
        return User.builder()
                .id(request.getUserId())
                .balance(request.getAmount())
                .build();
    }
}
