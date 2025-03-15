package com.is.customerfinance.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    private static final Pattern PATTERN = Pattern.compile(PASSWORD_REGEX);

    public static boolean isValid(String password) {
        return password != null && PATTERN.matcher(password).matches();
    }


    public static String getUrl(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getRequestURI().replace(httpServletRequest.getContextPath(), "");
    }
}
