CREATE TABLE section
(
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    user_details_id BIGINT REFERENCES user_details (id) ON DELETE CASCADE,
    UNIQUE (name, user_details_id)
);
