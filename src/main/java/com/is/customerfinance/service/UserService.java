package com.is.customerfinance.service;

import com.is.customerfinance.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<User> getAllUsers();

    Optional<User> getUserById(UUID id);

    Optional<User> getUserByUsername(String username);

    User createUser(User user);

    User updateUser(UUID id, User updatedUser);

    void deleteUser(UUID id);
}
