CREATE TABLE users
(
    id               SERIAL PRIMARY KEY,
    user_name        TEXT    NOT NULL UNIQUE,
    password         TEXT    NOT NULL,
    created_at       TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    last_time_online TIMESTAMP        DEFAULT CURRENT_TIMESTAMP NULL,
    is_enabled       boolean NOT NULL DEFAULT TRUE,
    role_id          INTEGER REFERENCES roles (id) ON DELETE CASCADE,
    UNIQUE (user_name)

);

