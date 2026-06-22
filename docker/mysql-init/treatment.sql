CREATE DATABASE treatment_db;
USE treatment_db;

-- patient_id y dentist_id son enteros planos, SIN foreign key (viven en
-- patient-service y dentist-service respectivamente).
CREATE TABLE tratamientos (
    id INT AUTO_INCREMENT PRIMARY KEY,

    patient_id INT NOT NULL,

    dentist_id INT NOT NULL,

    diagnostico TEXT NOT NULL,

    tipo_tratamiento VARCHAR(100) NOT NULL,

    costo_estimado DECIMAL(10,2) NOT NULL,

    fecha_inicio DATE NOT NULL,

    cant_sesiones INT NOT NULL,

    estado ENUM('PLANIFICADO', 'EN_PROGRESO', 'COMPLETADA', 'INTERRUMPIDO') NOT NULL DEFAULT 'PLANIFICADO'
);

-- treatment_id SI mantiene foreign key porque tratamientos y sesiones viven
-- en la misma base de datos de este microservicio (relacion intra-servicio).
CREATE TABLE sesiones_tratamiento (
    id INT AUTO_INCREMENT PRIMARY KEY,

    treatment_id INT NOT NULL,

    fecha_programada DATETIME,

    fecha_realizada DATETIME,

    observaciones TEXT,

    costo_parcial DECIMAL(10,2) NOT NULL DEFAULT 0,

    estado ENUM('PROGRAMADA', 'REALIZADA', 'CANCELADA') NOT NULL DEFAULT 'PROGRAMADA',

    CONSTRAINT fk_sesion_tratamiento
        FOREIGN KEY (treatment_id)
        REFERENCES tratamientos(id)
);
