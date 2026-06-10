--liquibase formatted sql

--changeset devvk:005_ddl_create_friend_requests_table
CREATE TABLE friend_requests
(
    id          SERIAL PRIMARY KEY,
    sender_id   INT         NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    receiver_id INT         NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    status      VARCHAR(20) NOT NULL,
    created_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CHECK (sender_id <> receiver_id),
    CHECK (status IN ('PENDING', 'ACCEPTED', 'REJECTED'))
);

--rollback DROP TABLE friend_requests;
