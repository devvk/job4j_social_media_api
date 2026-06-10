--liquibase formatted sql

--changeset devvk:004_ddl_create_subscribers_table
CREATE TABLE subscribers
(
    user_id       INT       NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    subscriber_id INT       NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, subscriber_id),
    CHECK (user_id <> subscriber_id)
);

--rollback DROP TABLE subscribers;
