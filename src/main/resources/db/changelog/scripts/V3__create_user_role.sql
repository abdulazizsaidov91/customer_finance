CREATE TABLE finance.user_role (
                           user_id UUID NOT NULL,
                           role_id BIGSERIAL NOT NULL,
                           CONSTRAINT fk_user_role_user FOREIGN KEY (user_id) REFERENCES finance.users(id),
                           CONSTRAINT fk_user_role_role FOREIGN KEY (role_id) REFERENCES finance.roles(id)
);
