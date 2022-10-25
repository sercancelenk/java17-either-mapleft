package com.example.java17.workshop.exceptionhandling.repository;

import com.example.java17.workshop.exceptionhandling.domain.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UserRepository {
    public Optional<User> getUser(String userId) {
        int rd = ThreadLocalRandom.current().nextInt(0, 10);
        return switch (rd) {
            case 0 -> throw new RuntimeException("some db error");
            case 1 -> Optional.empty();
            default -> Optional.of(new User(userId, "email"));
        };
    }
}
