package com.is.customerfinance.annatation;

import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Transactional(rollbackFor = Throwable.class)
@Documented
public @interface WriteTransactional {
}
