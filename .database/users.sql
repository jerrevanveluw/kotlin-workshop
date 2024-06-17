CREATE TABLE IF NOT EXISTS users
(
    id         bigint PRIMARY KEY NOT NULL,
    first_name varchar(255)       NOT NULL,
    last_name  varchar(255)       NOT NULL,
    birth_date varchar(255)       NOT NULL
);
INSERT INTO users(id, first_name, last_name, birth_date)
VALUES (0, 'first', 'last', '2000-01-01')
