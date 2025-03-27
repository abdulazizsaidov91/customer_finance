package com.is.customerfinance.enums;

import lombok.Getter;

@Getter
public enum TransactionType {
    CREDIT("Кредит", 1),
    DEBIT("Дебит", 2);

    private final String name;
    private final short id;

    TransactionType(String name, int id) {
        this.name = name;
        this.id = (short) id;
    }
}
