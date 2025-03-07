package com.is.customerfinance.service;

import com.is.customerfinance.model.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public interface TransactionService {
    List<Transaction> getAllTransactions();
    List<Transaction> getTransactionsByCustomerId(UUID customerID);
    Transaction credit(UUID customerId, BigDecimal amount, Locale locale);
    Transaction debit(UUID customerId, BigDecimal amount, Locale locale);
}
