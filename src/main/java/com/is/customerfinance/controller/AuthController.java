package com.is.customerfinance.controller;

import com.is.customerfinance.configuration.CustomUserDetails;
import com.is.customerfinance.dto.request.AuthRequest;
import com.is.customerfinance.dto.request.RefreshTokenRequest;
import com.is.customerfinance.dto.response.AuthResponse;
import com.is.customerfinance.exception.AuthException;
import com.is.customerfinance.filter.JwtService;
import com.is.customerfinance.model.RefreshToken;
import com.is.customerfinance.repository.UserRepository;
import com.is.customerfinance.service.CustomUserDetailsService;
import com.is.customerfinance.service.RefreshTokenService;
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
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request, Locale locale) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            String accessToken = jwtService.generateToken(userDetails, ((CustomUserDetails) userDetails).getId());
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(((CustomUserDetails) userDetails).getId(), locale);

            return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken.getToken()));
        } catch (AuthenticationException e) {
            throw new AuthException("access_denied", "wrong username or password");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenService.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (!refreshTokenService.isValid(refreshToken)) {
            refreshTokenService.deleteByUserId(refreshToken.getUser().getId());
            throw new RuntimeException("Refresh token expired. Please log in again.");
        }

        String accessToken = jwtService.generateToken(
                userDetailsService.loadUserByUsername(refreshToken.getUser().getUsername()),
                refreshToken.getUser().getId()
        );

        return ResponseEntity.ok(new AuthResponse(accessToken, request.getRefreshToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenService.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        refreshTokenService.deleteByUserId(refreshToken.getUser().getId());

        return ResponseEntity.ok("Logout successful");
    }
}
