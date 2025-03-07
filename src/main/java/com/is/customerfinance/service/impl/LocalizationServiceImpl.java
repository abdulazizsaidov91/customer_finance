package com.is.customerfinance.service.impl;

import com.is.customerfinance.service.LocalizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class LocalizationServiceImpl implements LocalizationService {
    private final MessageSource messageSource;

    @Override
    public String getMessage(String key, Locale locale) {
        return messageSource.getMessage(key, null, locale);
    }
}
