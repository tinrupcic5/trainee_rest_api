CREATE TABLE user_details
(
    id                                      SERIAL PRIMARY KEY,
    user_id                                 BIGINT REFERENCES users (id) ON DELETE CASCADE,
    name                                    TEXT NOT NULL,
    last_name                               TEXT NOT NULL,
    email                                   TEXT NULL,
    phone_number                            TEXT UNIQUE,
    school_details_id                       BIGINT REFERENCES school_details (id) ON DELETE CASCADE,
    training_level_classification_system_id BIGINT REFERENCES training_level_classification_system (id) ON DELETE CASCADE NULL,
    UNIQUE (id, user_id, training_level_classification_system_id)
);
