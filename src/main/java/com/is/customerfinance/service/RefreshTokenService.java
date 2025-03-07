package com.is.customerfinance.service;

import com.is.customerfinance.annatation.WriteTransactional;
import com.is.customerfinance.model.RefreshToken;
import com.is.customerfinance.repository.RefreshTokenRepository;
import com.is.customerfinance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final LocalizationService localizationService;

    @Value("${jwt.refresh-token-expiration}")
    private Long refreshTokenExpiration;

    @WriteTransactional
    public RefreshToken createRefreshToken(UUID userId, Locale locale) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userRepository.findById(userId).orElseThrow(() -> new RuntimeException(localizationService.getMessage("user.not.found", locale))))
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenExpiration))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @WriteTransactional
    public void deleteByUserId(UUID userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

    public boolean isValid(RefreshToken refreshToken) {
        return refreshToken.getExpiryDate().isAfter(Instant.now());
    }
}
