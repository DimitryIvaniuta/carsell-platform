CREATE TABLE users
(
    id         bigint             not null primary key,
    login      VARCHAR(50) UNIQUE NOT NULL,
    password   VARCHAR(255)       NOT NULL,
    name       text               NOT NULL,
    email      text UNIQUE        NOT NULL,
    first_name text               NOT NULL,
    last_name  text               NOT NULL,
    created_at timestamptz        not null DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamptz        not null DEFAULT CURRENT_TIMESTAMP
);