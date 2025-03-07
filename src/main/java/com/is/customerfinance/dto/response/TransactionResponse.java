package com.is.customerfinance.dto.response;

import com.is.customerfinance.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private String message;
    private Transaction data;
}
