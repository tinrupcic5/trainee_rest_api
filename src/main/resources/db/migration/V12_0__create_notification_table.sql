CREATE TABLE notification
(
    id SERIAL PRIMARY KEY,
    school_details_id BIGINT REFERENCES school_details (id) ON DELETE CASCADE,
    user_id      BIGINT REFERENCES users (id) ON DELETE CASCADE,
    message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
