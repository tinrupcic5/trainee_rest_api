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



-- For School 1
-- Morning Training Sessions
INSERT INTO training_details (name, type, training_level_classification_system_id, school_details_id, created_by,
                              training_time)
VALUES ('Advanced Nogi', 'MORNING', 1, 1, 1, '07:30:00');
INSERT INTO training_details (name, type, training_level_classification_system_id, school_details_id, created_by,
                              training_time)
VALUES ('Beginners Bjj', 'MORNING', 2, 1, 1, '09:00:00');

-- Midday Training Sessions
INSERT INTO training_details (name, type, training_level_classification_system_id, school_details_id, created_by,
                              training_time)
VALUES ('Kids Bjj', 'MIDDAY', 3, 1, 1, '12:00:00');
INSERT INTO training_details (name, type, training_level_classification_system_id, school_details_id, created_by,
                              training_time)
VALUES ('Gym All', 'MIDDAY', 1, 1, 1, '14:00:00');
INSERT INTO training_details (name, type, training_level_classification_system_id, school_details_id, created_by,
                              training_time)
VALUES ('Kids Nogi', 'MIDDAY', 3, 1, 1, '15:00:00');

-- Afternoon Training Sessions
INSERT INTO training_details (name, type, training_level_classification_system_id, school_details_id, created_by,
                              training_time)
VALUES ('Beginners Bjj', 'AFTERNOON', 2, 1, 1, '17:00:00');
INSERT INTO training_details (name, type, training_level_classification_system_id, school_details_id, created_by,
                              training_time)
VALUES ('Beginners Nogi', 'AFTERNOON', 2, 1, 1, '18:00:00');
INSERT INTO training_details (name, type, training_level_classification_system_id, school_details_id, created_by,
                              training_time)
VALUES ('Gym All', 'AFTERNOON', 1, 1, 1, '18:00:00');
INSERT INTO training_details (name, type, training_level_classification_system_id, school_details_id, created_by,
                              training_time)
VALUES ('Adults Bjj', 'AFTERNOON', 1, 1, 1, '19:15:00');
INSERT INTO training_details (name, type, training_level_classification_system_id, school_details_id, created_by,
                              training_time)
VALUES ('Open Mat - Bjj / Nogi', 'AFTERNOON', 4, 1, 1, '20:15:00');
