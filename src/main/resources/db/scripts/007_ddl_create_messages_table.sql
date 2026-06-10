--liquibase formatted sql

--changeset devvk:007_ddl_create_messages_table
CREATE TABLE messages
(
    id          BIGSERIAL PRIMARY KEY,
    sender_id   BIGINT    NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    receiver_id BIGINT    NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    content     TEXT      NOT NULL,
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CHECK (sender_id <> receiver_id)
);

--rollback DROP TABLE messages;
