CREATE TABLE qr_code
(
    id              SERIAL PRIMARY KEY,
    user_details_id BIGINT REFERENCES user_details (id) ON DELETE CASCADE UNIQUE,
    qr_code         TEXT NOT NULL UNIQUE,
    created         TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
