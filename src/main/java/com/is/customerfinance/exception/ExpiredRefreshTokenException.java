package com.is.customerfinance.exception;

public class ExpiredRefreshTokenException extends RefreshTokenException{
    public ExpiredRefreshTokenException() {
        super("Refresh token expired. Please log in again.");
    }
}
