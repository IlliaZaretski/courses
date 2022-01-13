CREATE TABLE IF NOT EXISTS locations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    address VARCHAR(255) UNIQUE NOT NULL,
    capacity TINYINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE courses
    ADD COLUMN location_id BIGINT;

ALTER TABLE courses
    ADD FOREIGN KEY (location_id)
        REFERENCES locations (id)
        ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE courses
    ADD CONSTRAINT UQ_LocationID_ID UNIQUE(location_id, id);