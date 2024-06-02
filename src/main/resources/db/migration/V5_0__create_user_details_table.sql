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

INSERT INTO user_details (user_Id, name, last_name, email, phone_number, school_details_id,
                          training_level_classification_system_id)
VALUES (1, 'Tin', 'Rupcic', 'tinrupcic5@gmail.com', '0916191178', 1, 1);

INSERT INTO user_details (user_Id, name, last_name, email, phone_number, school_details_id,
                          training_level_classification_system_id)
VALUES (2, 'Frane', 'Velcic', 'tinrupcic5@gmail.com', '0915805511', 2, 1);

INSERT INTO user_details (user_Id, name, last_name, email, phone_number, school_details_id,
                          training_level_classification_system_id)
VALUES (3, 'Haris', 'Ibrahimovic', 'tinrupcic5@gmail.com', '0989378852', 3, 2);
-- VALUES (3, 'Haris', 'Ibrahimovic', 'haro.ibrahimovic@gmail.com', '0989378852', 3, 2);

INSERT INTO user_details (user_Id, name, last_name, school_details_id)
VALUES (4, 'SCANNER', 'SCANNER', 1);
