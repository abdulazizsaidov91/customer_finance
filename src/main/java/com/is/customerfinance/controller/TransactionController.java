package com.is.customerfinance.controller;

import com.is.customerfinance.dto.response.TransactionResponse;
import com.is.customerfinance.model.Transaction;
import com.is.customerfinance.service.LocalizationService;
import com.is.customerfinance.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "03. Транзакции", description = "Методы получения информации о транзакциях")
public class TransactionController {
    private final TransactionService transactionService;
    private final LocalizationService localizationService;

    @Operation(summary = "Список", description = "Список транзакций")
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @Operation(summary = "Список", description = "Список транзакций клиента")
    @GetMapping("/customer/{customerID}")
    public ResponseEntity<List<Transaction>> getTransactionsByCustomer(@PathVariable UUID customerID) {
        return ResponseEntity.ok(transactionService.getTransactionsByCustomerId(customerID));
    }

    @Operation(summary = "Пополнение", description = "Пополнение счета клиента")
    @PostMapping("/credit/{customerId}")
    public ResponseEntity<?> credit(@PathVariable UUID customerId, @RequestParam BigDecimal amount, Locale locale) {
        Transaction transaction = transactionService.credit(customerId, amount, locale);
        String message = localizationService.getMessage("transaction.success", locale);
        return ResponseEntity.ok(new TransactionResponse(message, transaction));
    }

    @Operation(summary = "Списание", description = "Списание с баланса клиента")
    @PostMapping("/debit/{customerId}")
    public ResponseEntity<?> debit(@PathVariable UUID customerId, @RequestParam BigDecimal amount, Locale locale) {
        Transaction transaction = transactionService.debit(customerId, amount, locale);
        String message = localizationService.getMessage("transaction.success", locale);
        return ResponseEntity.ok(new TransactionResponse(message, transaction));
    }
}
