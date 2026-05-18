CREATE DATABASE dentist_db;
USE dentist_db;

CREATE TABLE specialties (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE dentists (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    user_id BIGINT NOT NULL,

    specialty_id BIGINT NOT NULL,

    license_number VARCHAR(50) NOT NULL UNIQUE,

    first_name VARCHAR(100) NOT NULL,

    last_name VARCHAR(100) NOT NULL,

    phone VARCHAR(20),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_dentist_specialty
        FOREIGN KEY (specialty_id)
        REFERENCES specialties(id)
);