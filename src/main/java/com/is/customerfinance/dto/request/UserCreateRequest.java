package com.is.customerfinance.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCreateRequest {
    @NotBlank(message = "Имя пользователя не должно быть пустым.")
    private String userName;

    @NotBlank(message = "Пароль не должен быть пустым.")
    @Size(min = 8, message = "Пароль должен содержать минимум 8 символов.")
    private String password;

    @NotBlank(message = "Подтверждение пароля не должно быть пустым.")
    private String confirmPassword;

    private Set<String> roles;
}
