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

INSERT INTO users (user_name, password, role_id)
VALUES ('tin', '$2a$12$k7ndDdaI8NRONVfNWizyUePQeb5crzK.mSmuN1YYaJPaxC2XS2vEG', 1);

INSERT INTO users (user_name, password, role_id)
VALUES ('frane', '$2a$12$8weSaZLUI0.XxF9UoE5ew.Z63kz7P0J7YqFaw4/cliMldG.kspxX.', 2);

INSERT INTO users (user_name, password, role_id)
VALUES ('haris', '$2a$12$FBAsogLP8UfxY0ANN3T5he9rv4W0bn1dFilM2ruabKkDAncmI6GoC', 3);

INSERT INTO users (user_name, password, role_id)
VALUES ('SCANNER', '$2a$12$rmIgI7UqLBmmUogjZ6xHt.B/RTTH4xJ02rj8mCUeukXtNhIJZJdla', 5);
