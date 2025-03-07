CREATE TABLE finance.refresh_token (
                               id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                               user_id UUID NOT NULL REFERENCES finance.users(id) ON DELETE CASCADE,
                               token TEXT NOT NULL UNIQUE,
                               expiry_date TIMESTAMP NOT NULL
);
