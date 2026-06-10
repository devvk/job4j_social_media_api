--liquibase formatted sql

--changeset devvk:003_ddl_create_images_table
CREATE TABLE images
(
    id         SERIAL PRIMARY KEY,
    path       VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    post_id    INT          NOT NULL REFERENCES posts (id) ON DELETE CASCADE
);

--rollback DROP TABLE images;
