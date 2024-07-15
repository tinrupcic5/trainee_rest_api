CREATE TABLE training_level_classification_system
(
    id            SERIAL PRIMARY KEY,
    training_level_classification    TEXT NOT NULL,
    UNIQUE (training_level_classification)
);


