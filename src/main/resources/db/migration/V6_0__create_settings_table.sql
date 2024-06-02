CREATE TABLE settings
(
    id                         SERIAL PRIMARY KEY,
    user_details_id            BIGINT REFERENCES user_details (id) ON DELETE CASCADE,
    language                   TEXT    NOT NULL DEFAULT 'EN',
    is_registration_email_sent BOOLEAN NOT NULL DEFAULT FALSE,
    UNIQUE (user_details_id, language)
);

INSERT INTO settings(user_details_id, language)
VALUES (1, 'HR');
INSERT INTO settings(user_details_id, language)
VALUES (2, 'HR');
INSERT INTO settings(user_details_id, language)
VALUES (3, 'HR');
INSERT INTO settings(user_details_id, language, is_registration_email_sent)
VALUES (4, 'EN', true );
