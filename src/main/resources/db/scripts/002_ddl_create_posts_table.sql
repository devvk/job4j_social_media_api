--liquibase formatted sql

--changeset devvk:002_ddl_create_posts_table
CREATE TABLE posts
(
    id         BIGSERIAL PRIMARY KEY,
    title      VARCHAR(255) NOT NULL,
    body       TEXT         NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    author_id  BIGINT       NOT NULL REFERENCES users (id)
);

--rollback DROP TABLE posts;
