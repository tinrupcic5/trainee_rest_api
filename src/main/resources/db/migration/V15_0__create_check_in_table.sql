CREATE TABLE check_in
(
    id            SERIAL PRIMARY KEY,
    qr_code_id    BIGINT REFERENCES qr_code (id) ON DELETE CASCADE,
    check_in_date DATE DEFAULT CURRENT_DATE,
    check_in_time TIME DEFAULT CURRENT_TIME,
    UNIQUE (qr_code_id, check_in_date)
);
