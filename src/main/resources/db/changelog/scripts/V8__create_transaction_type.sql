CREATE TABLE finance.transaction_type (
                                          id SMALLINT PRIMARY KEY,
                                          name VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO finance.transaction_type (id, name) VALUES
                                                    (1, 'CREDIT'),
                                                    (2, 'DEBIT');
