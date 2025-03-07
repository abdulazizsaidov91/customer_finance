CREATE TABLE finance.transactions (
                              id BIGSERIAL PRIMARY KEY,
                              customer_id UUID NOT NULL,
                              amount DECIMAL(18,2) NOT NULL,
                              timestamp TIMESTAMP NOT NULL DEFAULT NOW(),
                              CONSTRAINT fk_transaction_customer FOREIGN KEY (customer_id) REFERENCES finance.customers(id)
);
