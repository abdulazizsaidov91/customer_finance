package com.is.customerfinance.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AuthException extends RuntimeException {
    private final String error;
    @JsonProperty("error_description")
    private final String errorDescription;

    public AuthException(String error, String errorDescription) {
        this.error = error;
        this.errorDescription = errorDescription;
    }
}
