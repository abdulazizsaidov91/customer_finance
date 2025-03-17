package com.is.customerfinance.exception;

public class RefreshTokenNotFoundException extends RefreshTokenException{
    public RefreshTokenNotFoundException() {
        super("Refresh token not found");
    }
}
