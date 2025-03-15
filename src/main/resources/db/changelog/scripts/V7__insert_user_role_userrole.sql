INSERT INTO finance.users (id, username, password)
VALUES ('f104c2ff-3eab-40a5-88e7-1f57dc1c4560', 'admin',
        '$2a$12$83OavAc6Lu.TzxrScEp5WuCvc6/htrCHhGzVV6kmXgVgUUC.7aCf.');
INSERT INTO finance.roles (id, name)
VALUES (1, 'admin');
INSERT INTO finance.roles (id, name)
VALUES (2, 'operator');
INSERT INTO finance.roles (id, name)
VALUES (3, 'cashier');
INSERT INTO finance.user_role (user_id, role_id)
VALUES ('f104c2ff-3eab-40a5-88e7-1f57dc1c4560', 1);
INSERT INTO finance.user_role (user_id, role_id)
VALUES ('f104c2ff-3eab-40a5-88e7-1f57dc1c4560', 2);
INSERT INTO finance.user_role (user_id, role_id)
VALUES ('f104c2ff-3eab-40a5-88e7-1f57dc1c4560', 3);