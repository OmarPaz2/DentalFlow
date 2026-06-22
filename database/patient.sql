CREATE DATABASE patient_db;
CREATE TYPE gender_enum AS ENUM (
    'M',
    'F'
);

CREATE TABLE patients (
                          id BIGSERIAL PRIMARY KEY,

                          dni VARCHAR(15) NOT NULL UNIQUE,

                          first_name VARCHAR(100) NOT NULL,

                          last_name VARCHAR(100) NOT NULL,

                          birth_date DATE,

                          gender gender_enum,

                          phone VARCHAR(20),

                          email VARCHAR(100),

                          address VARCHAR(255),

                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);