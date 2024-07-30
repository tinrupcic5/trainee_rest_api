CREATE TABLE school
(
    id          SERIAL PRIMARY KEY,
    school_name TEXT NOT NULL
);

CREATE TABLE school_details
(
    id              SERIAL PRIMARY KEY,
    school_Id       BIGINT REFERENCES school (id) ON DELETE CASCADE,
    school_location TEXT NOT NULL,
    school_country  TEXT NOT NULL
);

