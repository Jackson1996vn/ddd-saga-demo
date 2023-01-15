package com.example.paymentcore.infrastructure.adapter;

import com.example.paymentcore.domain.model.User;
import com.example.paymentcore.domain.port.outgoing.UserOutgoing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepository implements UserOutgoing {

    private final UserJPARepository jpaRepository;

    @Override
    public User createUser(User user) {
        return jpaRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public User deposit(User user) {
        Optional<User> optionalUser = jpaRepository.findById(user.getId());
        if (optionalUser.isPresent()) {
            User updated = optionalUser.get();
            updated.setBalance(updated.getBalance() + user.getBalance());
            return jpaRepository.save(updated);
        }
        return null;
    }

    @Override
    public User withdraw(User user) {
        Optional<User> optionalUser = jpaRepository.findById(user.getId());
        if (optionalUser.isPresent()
                && optionalUser.get().getBalance() >= user.getBalance()) {
            User updated = optionalUser.get();
            updated.setBalance(updated.getBalance() - user.getBalance());
            return jpaRepository.save(updated);
        }
        return null;
    }
}
