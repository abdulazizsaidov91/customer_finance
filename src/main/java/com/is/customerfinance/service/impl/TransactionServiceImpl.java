package com.is.customerfinance.service.impl;

import com.is.customerfinance.annatation.WriteTransactional;
import com.is.customerfinance.exception.BadRequestException;
import com.is.customerfinance.model.Customer;
import com.is.customerfinance.model.Transaction;
import com.is.customerfinance.repository.CustomerRepository;
import com.is.customerfinance.repository.TransactionRepository;
import com.is.customerfinance.service.LocalizationService;
import com.is.customerfinance.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;
    private final LocalizationService localizationService;

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> getTransactionsByCustomerId(UUID customerId) {
        return transactionRepository.findByCustomerId(customerId);
    }

    @Override
    @WriteTransactional
    public Transaction credit(UUID customerId, BigDecimal amount, Locale locale) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new BadRequestException("Customer Not Found", localizationService.getMessage("customer.not.found", locale)));

        customer.setBalance(customer.getBalance().add(amount));
        customerRepository.save(customer);

        Transaction transaction = Transaction.builder()
                .customer(customer)
                .amount(amount)
                .timestamp(LocalDateTime.now())
                .build();

        return transactionRepository.save(transaction);
    }

    @Override
    @WriteTransactional
    public Transaction debit(UUID customerId, BigDecimal amount, Locale locale) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new BadRequestException("Customer Not Found", localizationService.getMessage("customer.not.found", locale)));

        if (customer.getBalance().compareTo(amount) < 0) {
            throw new BadRequestException("Insufficient funds", localizationService.getMessage("insufficient.funds", locale));
        }

        customer.setBalance(customer.getBalance().subtract(amount));
        customerRepository.save(customer);

        Transaction transaction = Transaction.builder()
                .customer(customer)
                .amount(amount.negate())
                .timestamp(LocalDateTime.now())
                .build();

        return transactionRepository.save(transaction);
    }
}
