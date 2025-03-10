package com.is.customerfinance.util;

import jakarta.servlet.http.HttpServletRequest;

public class Utils {
    public static String getUrl(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getRequestURI().replace(httpServletRequest.getContextPath(), "");
    }
}
