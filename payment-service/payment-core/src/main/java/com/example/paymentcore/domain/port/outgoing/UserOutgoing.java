package com.example.paymentcore.domain.port.outgoing;

import com.example.paymentcore.domain.model.User;

import java.util.List;

public interface UserOutgoing {

    User createUser(User user);

    List<User> findAll();

    User deposit(User user);

    User withdraw(User user);
}
