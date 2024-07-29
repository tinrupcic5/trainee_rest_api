CREATE TABLE file_details
(
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    section_id BIGINT REFERENCES section (id) ON DELETE CASCADE,
    file_view_status TEXT NOT NULL,
    suffix TEXT NOT NULL,
    class_path TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    file_type TEXT NOT NULL, --- video or image
    comment TEXT NULL,
    UNIQUE (name, section_id)
);
