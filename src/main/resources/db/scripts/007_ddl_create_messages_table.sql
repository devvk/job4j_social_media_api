--liquibase formatted sql

--changeset devvk:007_ddl_create_messages_table
CREATE TABLE messages
(
    id          SERIAL PRIMARY KEY,
    sender_id   INT       NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    receiver_id INT       NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    message     TEXT      NOT NULL,
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CHECK (sender_id <> receiver_id)
);

--rollback DROP TABLE messages;
