alter table finance.transactions
    add transaction_type smallint not null
        constraint fk_transactions_type
            references finance.transaction_type (id);