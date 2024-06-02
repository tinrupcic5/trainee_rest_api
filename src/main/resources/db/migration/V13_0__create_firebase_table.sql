CREATE TABLE firebase
(
    firebase_id SERIAL PRIMARY KEY,
    user_id      BIGINT REFERENCES users (id) ON DELETE CASCADE,
    firebase_token  TEXT NOT NULL
);
