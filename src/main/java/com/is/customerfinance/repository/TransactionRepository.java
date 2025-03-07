package com.is.customerfinance.repository;

import com.is.customerfinance.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByCustomerId(UUID customerID);

    List<Transaction> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
