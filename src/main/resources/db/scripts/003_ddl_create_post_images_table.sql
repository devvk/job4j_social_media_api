--liquibase formatted sql

--changeset devvk:003_ddl_create_post_images_table
CREATE TABLE post_images
(
    id         BIGSERIAL PRIMARY KEY,
    path       VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    post_id    BIGINT       NOT NULL REFERENCES posts (id) ON DELETE CASCADE
);

--rollback DROP TABLE post_images;
