--liquibase formatted sql

--changeset devvk:002_ddl_create_posts_table
CREATE TABLE posts
(
    id         SERIAL PRIMARY KEY,
    title      VARCHAR(255) NOT NULL,
    body       TEXT         NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id    INT          NOT NULL REFERENCES users (id)
);

--rollback DROP TABLE posts;
