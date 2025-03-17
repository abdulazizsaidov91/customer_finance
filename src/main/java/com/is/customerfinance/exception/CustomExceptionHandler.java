package com.is.customerfinance.exception;

import com.is.customerfinance.dto.response.BaseBadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;


@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseBadException badRequest(BadRequestException e) {
        return new BaseBadException(e.getError(), e.getErrorDescription());
    }

    @ExceptionHandler(ExpiredRefreshTokenException.class)
    public ResponseEntity<Object> handleExpiredRefreshToken(ExpiredRefreshTokenException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ResponseEntity<Object> handleRefreshTokenNotFound(RefreshTokenNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", ex.getMessage()));
    }
}
