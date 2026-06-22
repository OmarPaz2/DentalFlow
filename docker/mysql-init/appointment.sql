CREATE DATABASE appointment_db;
USE appointment_db;

CREATE TABLE appointment_types (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    name VARCHAR(100) NOT NULL UNIQUE,

    duration_minutes INT NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- patient_id y dentist_id son enteros planos, SIN foreign key: el paciente
-- vive en patient-service y el personal clinico en dentist-service, cada
-- uno con su propia base de datos.
CREATE TABLE citas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    patient_id INT NOT NULL,

    dentist_id INT NOT NULL,

    appointment_type_id BIGINT NOT NULL,

    appointment_date DATE NOT NULL,

    start_time TIME NOT NULL,

    end_time TIME NOT NULL,

    reason VARCHAR(255),

    status ENUM('PENDIENTE', 'CONFIRMADA', 'REPROGRAMADA', 'CANCELADA', 'COMPLETADA') NOT NULL DEFAULT 'PENDIENTE',

    amount DECIMAL(10,2) NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_cita_appointment_type
        FOREIGN KEY (appointment_type_id)
        REFERENCES appointment_types(id)
);
