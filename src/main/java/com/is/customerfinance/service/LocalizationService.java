package com.is.customerfinance.service;

import java.util.Locale;

public interface LocalizationService {
    String getMessage(String key, Locale locale);
}
