CREATE DATABASE dentist_db;
USE dentist_db;

-- specialty_id es un entero plano, SIN foreign key: la especialidad ahora
-- vive en specialty-service (su propia base de datos, ver specialty.sql).
-- license_number es NULL para personal que no es odontologo (administrador,
-- recepcionista), por eso ya no es NOT NULL.
CREATE TABLE clinical_staff (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    user_id BIGINT,

    specialty_id BIGINT,

    license_number VARCHAR(50) UNIQUE,

    first_name VARCHAR(100) NOT NULL,

    last_name VARCHAR(100) NOT NULL,

    phone VARCHAR(20),

    staff_type ENUM('ADMINISTRADOR', 'RECEPCIONISTA', 'ODONTOLOGO') NOT NULL,

    available BOOLEAN DEFAULT TRUE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
