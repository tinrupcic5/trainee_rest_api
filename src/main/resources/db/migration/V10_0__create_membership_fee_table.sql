CREATE TABLE membership_fee_amount
(
    id                SERIAL PRIMARY KEY,
    school_details_id BIGINT REFERENCES school_details (id) ON DELETE CASCADE,
    amount            DECIMAL(10, 2) NOT NULL,
    currency          TEXT           NOT NULL,
    UNIQUE (school_details_id, amount)
);

CREATE TABLE membership_fee
(
    id                       SERIAL PRIMARY KEY,
    user_details_id          BIGINT REFERENCES user_details (id) ON DELETE CASCADE,
    payment_date             TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    for_month                INT              DEFAULT NULL,
    valid_until              TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    membership_fee_amount_id BIGINT REFERENCES membership_fee_amount (id) ON DELETE CASCADE,
    is_paid                  BOOLEAN NOT NULL DEFAULT FALSE,
    is_email_sent            BOOLEAN NOT NULL DEFAULT FALSE,
    email_sent_date          TIMESTAMP NULL
);

INSERT INTO membership_fee_amount (school_details_id, amount, currency)
VALUES (1, 40, 'euro');
