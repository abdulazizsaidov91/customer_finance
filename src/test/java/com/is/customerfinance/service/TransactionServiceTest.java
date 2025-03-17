package com.is.customerfinance.service;

import com.is.customerfinance.exception.BadRequestException;
import com.is.customerfinance.model.Customer;
import com.is.customerfinance.model.Transaction;
import com.is.customerfinance.repository.CustomerRepository;
import com.is.customerfinance.repository.TransactionRepository;
import com.is.customerfinance.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository; // Мокаем репозиторий транзакций

    @Mock
    private CustomerRepository customerRepository; // Мокаем репозиторий клиентов

    @Mock
    private LocalizationService localizationService;

    @InjectMocks
    private TransactionServiceImpl transactionService; // Тестируемый сервис (зависимости подтянутся автоматически)

    private UUID customerId;
    private Customer customer;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();
        customer = new Customer();
        customer.setId(customerId);
        customer.setBalance(BigDecimal.valueOf(1000)); // У клиента баланс 1000
    }

    @Test
    void credit_ShouldIncreaseBalance() {
        // ДАНО
        BigDecimal creditAmount = BigDecimal.valueOf(500); // Пополняем 500
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any())).thenReturn(customer);
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // ДЕЙСТВИЕ
        Transaction transaction = transactionService.credit(customerId, creditAmount, Locale.ENGLISH);

        // ПРОВЕРКА
        assertNotNull(transaction); // Транзакция должна существовать
        assertEquals(BigDecimal.valueOf(1500), customer.getBalance()); // Баланс должен стать 1500
        verify(transactionRepository, times(1)).save(any(Transaction.class)); // Проверяем, что транзакция сохранилась
    }

    @Test
    void debit_ShouldDecreaseBalance_WhenEnoughFunds() {
        // ДАНО
        BigDecimal debitAmount = BigDecimal.valueOf(700); // Списываем 700
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any())).thenReturn(customer);
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // ДЕЙСТВИЕ
        Transaction transaction = transactionService.debit(customerId, debitAmount, Locale.ENGLISH);

        // ПРОВЕРКА
        assertNotNull(transaction);
        assertEquals(BigDecimal.valueOf(300), customer.getBalance()); // Баланс должен стать 300
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }


    @Test
    void debit_ShouldThrowException_WhenInsufficientFunds() {
        // ДАНО
        BigDecimal debitAmount = BigDecimal.valueOf(1500); // Пытаемся списать 1500 (больше, чем есть)
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(localizationService.getMessage(eq("insufficient.funds"), any())).thenReturn("Insufficient funds");

        // ДЕЙСТВИЕ И ПРОВЕРКА (ожидаем ошибку)
        var exception = assertThrows(BadRequestException.class, () ->
                transactionService.debit(customerId, debitAmount, Locale.ENGLISH)
        );

        // ПРОВЕРКА
        assertEquals("Insufficient funds", exception.getError()); // Ошибка должна быть правильной
        assertEquals(BigDecimal.valueOf(1000), customer.getBalance()); // Баланс не должен измениться
        verify(transactionRepository, never()).save(any(Transaction.class)); // Транзакция не должна сохраняться
    }

}
