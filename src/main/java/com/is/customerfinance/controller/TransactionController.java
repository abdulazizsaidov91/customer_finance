package com.is.customerfinance.controller;

import com.is.customerfinance.model.Transaction;
import com.is.customerfinance.dto.response.TransactionResponse;
import com.is.customerfinance.service.LocalizationService;
import com.is.customerfinance.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/api/private/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final LocalizationService localizationService;

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @GetMapping("/user/{customerID}")
    public ResponseEntity<List<Transaction>> getTransactionsByCustomer(@PathVariable UUID customerID) {
        return ResponseEntity.ok(transactionService.getTransactionsByCustomerId(customerID));
    }

    @PostMapping("/credit/{customerId}")
    public ResponseEntity<?> credit(@PathVariable UUID customerId, @RequestParam BigDecimal amount, Locale locale) {
        Transaction transaction = transactionService.credit(customerId, amount, locale);
        String message = localizationService.getMessage("transaction.success", locale);
        return ResponseEntity.ok(new TransactionResponse(message, transaction));
    }

    @PostMapping("/debit/{customerId}")
    public ResponseEntity<?> debit(@PathVariable UUID customerId, @RequestParam BigDecimal amount, Locale locale) {
        Transaction transaction = transactionService.debit(customerId, amount, locale);
        String message = localizationService.getMessage("transaction.success", locale);
        return ResponseEntity.ok(new TransactionResponse(message, transaction));
    }
}
