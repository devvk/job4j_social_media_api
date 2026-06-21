--liquibase formatted sql

--changeset devvk:010_dml_insert_roles
INSERT INTO roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN'),
       ('ROLE_MODERATOR');

--rollback DELETE FROM roles WHERE name IN ('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR');
