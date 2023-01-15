package com.example.paymentcore.application.controller;

import com.example.paymentcore.domain.model.request.CreateUserRequest;
import com.example.paymentcore.domain.model.request.DepositRequest;
import com.example.paymentcore.domain.model.request.WithdrawRequest;
import com.example.paymentcore.domain.model.response.UserResponse;
import com.example.paymentcore.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/payment")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> findAllUser() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(@RequestBody final CreateUserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PostMapping("/users/deposit")
    public ResponseEntity<UserResponse> deposit(@RequestBody final DepositRequest request) {
        return ResponseEntity.ok(userService.deposit(request));
    }

    @PostMapping("/users/withdraw")
    public ResponseEntity<UserResponse> withdraw(@RequestBody final WithdrawRequest request) {
        return ResponseEntity.ok(userService.withdraw(request));
    }
}
