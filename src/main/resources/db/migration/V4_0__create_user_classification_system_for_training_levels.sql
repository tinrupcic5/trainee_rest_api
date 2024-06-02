CREATE TABLE training_level_classification_system
(
    id            SERIAL PRIMARY KEY,
    training_level_classification    TEXT NOT NULL,
    UNIQUE (training_level_classification)
);


INSERT INTO training_level_classification_system (training_level_classification)
VALUES ('ADULTS');
INSERT INTO training_level_classification_system (training_level_classification)
VALUES ('BEGINNERS');
INSERT INTO training_level_classification_system (training_level_classification)
VALUES ('KIDS');
INSERT INTO training_level_classification_system (training_level_classification)
VALUES ('ALL');



