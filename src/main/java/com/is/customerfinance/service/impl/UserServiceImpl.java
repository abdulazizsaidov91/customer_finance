package com.is.customerfinance.service.impl;

import com.is.customerfinance.annatation.ReadTransactional;
import com.is.customerfinance.annatation.WriteTransactional;
import com.is.customerfinance.dto.request.UserCreateRequest;
import com.is.customerfinance.exception.BadRequestException;
import com.is.customerfinance.model.Role;
import com.is.customerfinance.model.User;
import com.is.customerfinance.repository.RoleRepository;
import com.is.customerfinance.repository.UserRepository;
import com.is.customerfinance.service.LocalizationService;
import com.is.customerfinance.service.UserService;
import com.is.customerfinance.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final LocalizationService localizationService;

    @Override
    @ReadTransactional
    public List<User> getAllUsers() {
        return userRepository.findAllWithRoles();
    }

    @ReadTransactional
    @Override
    public Optional<User> getUserById(UUID id) {
        return userRepository.findByIdWithRoles(id);
    }

    @ReadTransactional
    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @WriteTransactional
    public User createUser(UserCreateRequest request) {
        checkUserDatas(request); // Проверка пароля на валидность

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        Set<Role> userRoles = request.getRoles().stream()
                .map(roleName -> roleRepository.findByNameIgnoreCase(roleName)
                        .orElseThrow(() -> new BadRequestException("Invalid datas", "Роль " + roleName + " не найдена"))
                )
                .collect(Collectors.toSet());
        var user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(request.getUserName().toLowerCase());
        user.setPassword(hashedPassword);
        user.setRoles(userRoles);
        return userRepository.save(user);
    }

    private void checkUserDatas(UserCreateRequest data) throws BadRequestException {
        if (!isPasswordMatching(data.getPassword(), data.getConfirmPassword()))
            throw new BadRequestException("Invalid datas", "Пароль и подтверждение пароля не совпадают.");

        if (!Utils.isValid(data.getPassword()))
            throw new BadRequestException("Invalid datas", "Пароль должен содержать минимум 8 символов, хотя бы одну цифру, одну заглавную и одну строчную латинскую букву.");
    }

    public static boolean isPasswordMatching(String password, String confirmPassword) {
        return password != null && password.equals(confirmPassword);
    }

    @Override
    @WriteTransactional
    public User updateUser(UUID id, User updatedUser, Locale locale) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(updatedUser.getUsername());
                    user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                    user.setRoles(updatedUser.getRoles());
                    return userRepository.save(user);
                }).orElseThrow(() -> new BadRequestException("Not Found", localizationService.getMessage("user.not.found", locale)));
    }

    @Override
    @WriteTransactional
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

}
