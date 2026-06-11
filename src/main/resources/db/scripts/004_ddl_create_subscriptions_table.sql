--liquibase formatted sql

--changeset devvk:004_ddl_create_subscriptions_table
CREATE TABLE subscriptions
(
    id            BIGSERIAL PRIMARY KEY,
    user_id       BIGINT    NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    subscriber_id BIGINT    NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (user_id, subscriber_id),
    CHECK (user_id <> subscriber_id)
);

--rollback DROP TABLE subscriptions;
