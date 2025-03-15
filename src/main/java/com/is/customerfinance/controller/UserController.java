package com.is.customerfinance.controller;

import com.is.customerfinance.dto.request.UserCreateRequest;
import com.is.customerfinance.model.User;
import com.is.customerfinance.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/private/users")
@RequiredArgsConstructor
@Tag(name = "04. Пользователи", description = "Методы получения информации о пользователях")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Список пользователей", description = "Получение списка пользователей")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Информация о пользователях", description = "Информация о пользователе по ID")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Создание", description = "Создание пользователя")
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserCreateRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @Operation(summary = "Изменение", description = "Изменение данных пользователя по ID")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @Operation(summary = "Удаление", description = "Удаление пользователя по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
