CREATE TABLE training_details
(
    id                                      SERIAL PRIMARY KEY,
    name                                    TEXT                                                                          NOT NULL,
    -- type -morning(00-12) / midday(11-17) / afternoon(17-00)
    type                                    TEXT                                                                          NOT NULL,
    training_level_classification_system_id BIGINT REFERENCES training_level_classification_system (id) ON DELETE CASCADE NOT NULL,
    school_details_id                       BIGINT REFERENCES school_details (id) ON DELETE CASCADE,
    created_by                              BIGINT REFERENCES user_details (id) ON DELETE CASCADE,
    created_at                              TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    training_time                           time                                                                          NOT NULL
);

CREATE TABLE training_calendar
(
    id                  SERIAL PRIMARY KEY,
    training_date       DATE    NOT NULL,
    training_details_id BIGINT REFERENCES training_details (id) ON DELETE CASCADE,
    -- canceled / active
    training_status     BOOLEAN NOT NULL DEFAULT TRUE,
    UNIQUE (training_date, training_details_id)
);

CREATE TABLE training_participation
(
    id                   SERIAL PRIMARY KEY,
    training_calendar_id BIGINT REFERENCES training_calendar (id) ON DELETE CASCADE,
    user_details_id      BIGINT REFERENCES user_details (id) ON DELETE CASCADE,
    attended_status      boolean NOT NULL DEFAULT TRUE,
    UNIQUE (training_calendar_id, user_details_id)
);


