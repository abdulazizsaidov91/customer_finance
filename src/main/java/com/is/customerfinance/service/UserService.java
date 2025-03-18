package com.is.customerfinance.service;

import com.is.customerfinance.dto.request.UserCreateRequest;
import com.is.customerfinance.model.User;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<User> getAllUsers();

    Optional<User> getUserById(UUID id);

    Optional<User> getUserByUsername(String username);

    User createUser(UserCreateRequest request);

    User updateUser(UUID id, User updatedUser, Locale locale);

    void deleteUser(UUID id);
}
