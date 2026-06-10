--liquibase formatted sql

--changeset devvk:001_ddl_create_users_table
CREATE TABLE users
(
    id            SERIAL PRIMARY KEY,
    username      VARCHAR(255) UNIQUE NOT NULL,
    email         VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255)        NOT NULL,
    created_at    TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--rollback DROP TABLE users;
