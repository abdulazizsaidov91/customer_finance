package com.is.customerfinance.controller;

import com.is.customerfinance.configuration.CustomUserDetails;
import com.is.customerfinance.dto.request.AuthRequest;
import com.is.customerfinance.dto.request.RefreshTokenRequest;
import com.is.customerfinance.dto.response.AuthResponse;
import com.is.customerfinance.exception.BadRequestException;
import com.is.customerfinance.exception.ExpiredRefreshTokenException;
import com.is.customerfinance.exception.RefreshTokenNotFoundException;
import com.is.customerfinance.filter.JwtService;
import com.is.customerfinance.model.RefreshToken;
import com.is.customerfinance.service.CustomUserDetailsService;
import com.is.customerfinance.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
@Tag(name = "01. Методы аутентификации", description = "Методы аутентификации")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final RefreshTokenService refreshTokenService;

    @Operation(summary = "Получение токена", description = "Получение токена авторизации")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request, Locale locale) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername().toLowerCase(), request.getPassword())
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            String accessToken = jwtService.generateToken(userDetails, ((CustomUserDetails) userDetails).getId());
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(((CustomUserDetails) userDetails).getId(), locale);

            return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken.getToken()));
        } catch (AuthenticationException e) {
            throw new BadRequestException("access_denied", "wrong username or password");
        }
    }

    @Operation(summary = "Обновление токена", description = "Обновление токена авторизации")
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenService.findByToken(request.getRefreshToken())
                .orElseThrow(RefreshTokenNotFoundException::new);

        if (refreshTokenService.isExpired(refreshToken)) {
            refreshTokenService.deleteByUserId(refreshToken.getUser().getId());
            throw new ExpiredRefreshTokenException();
        }
        String accessToken = jwtService.generateToken(
                userDetailsService.loadUserByUsername(refreshToken.getUser().getUsername()),
                refreshToken.getUser().getId()
        );
        return ResponseEntity.ok(new AuthResponse(accessToken, request.getRefreshToken()));
    }

    @Operation(summary = "Выход", description = "Выход из системы")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenService.findByToken(request.getRefreshToken())
                .orElseThrow(RefreshTokenNotFoundException::new);
        refreshTokenService.deleteByUserId(refreshToken.getUser().getId());
        return ResponseEntity.ok("Logout successful");
    }
}
