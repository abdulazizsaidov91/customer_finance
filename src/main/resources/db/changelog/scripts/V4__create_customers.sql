CREATE TABLE finance.customers (
                           id UUID PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           balance DECIMAL(18,2) DEFAULT 0.00
);
