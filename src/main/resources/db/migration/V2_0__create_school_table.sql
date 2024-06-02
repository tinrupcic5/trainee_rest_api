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

INSERT INTO school (school_name)
VALUES ('JJK Pura Vida');
INSERT INTO school (school_name)
VALUES ('JJK Arrow');

INSERT INTO school_details (school_Id, school_location, school_country)
VALUES (1, 'Cres', 'CRO');
INSERT INTO school_details (school_Id, school_location, school_country)
VALUES (1, 'Rijeka', 'CRO');
INSERT INTO school_details (school_Id, school_location, school_country)
VALUES (2, 'Zagreb', 'CRO');
