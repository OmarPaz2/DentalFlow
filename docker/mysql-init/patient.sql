CREATE DATABASE patient_db;
USE patient_db;

CREATE TABLE patients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    dni VARCHAR(15) NOT NULL UNIQUE,

    first_name VARCHAR(100) NOT NULL,

    last_name VARCHAR(100) NOT NULL,

    birth_date DATE,

    gender ENUM('M','F'),

    phone VARCHAR(20),

    email VARCHAR(100),

    address VARCHAR(255),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);