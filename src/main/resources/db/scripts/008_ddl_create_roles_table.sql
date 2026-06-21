--liquibase formatted sql

--changeset devvk:008_ddl_create_roles_table
CREATE TABLE roles
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

--rollback DROP TABLE roles;
