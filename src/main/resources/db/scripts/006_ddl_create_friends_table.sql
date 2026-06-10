--liquibase formatted sql

--changeset devvk:006_ddl_create_friends_table
CREATE TABLE friends
(
    id         BIGSERIAL PRIMARY KEY,
    user1_id   BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    user2_id   BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    created_at TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (user1_id, user2_id),
    CHECK (user1_id < user2_id)
);

--rollback DROP TABLE friends;
