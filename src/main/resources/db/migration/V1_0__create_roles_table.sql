CREATE TABLE roles (
                       id SERIAL PRIMARY KEY,
                       role_name TEXT NOT NULL
);

INSERT INTO roles (role_name) VALUES ('JEDI');
INSERT INTO roles (role_name) VALUES ('PROFESSOR');
INSERT INTO roles (role_name) VALUES ('STUDENT');
INSERT INTO roles (role_name) VALUES ('VIEWER');
INSERT INTO roles (role_name) VALUES ('SCANNER');
