--liquibase formatted sql

--changeset devvk:009_ddl_create_users_roles_table
CREATE TABLE users_roles
(
    user_id BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    role_id INT    NOT NULL REFERENCES roles (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

--rollback DROP TABLE users_roles;
