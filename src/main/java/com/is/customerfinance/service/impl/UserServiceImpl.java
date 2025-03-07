package com.is.customerfinance.service.impl;

import com.is.customerfinance.annatation.WriteTransactional;
import com.is.customerfinance.model.User;
import com.is.customerfinance.repository.UserRepository;
import com.is.customerfinance.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @WriteTransactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @WriteTransactional
    public User updateUser(UUID id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(updatedUser.getUsername());
                    user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                    user.setRoles(updatedUser.getRoles());
                    return userRepository.save(user);
                }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    @WriteTransactional
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

}
