CREATE DATABASE payment_db;
USE payment_db;

-- treatment_id y appointment_id son enteros planos, SIN foreign key (viven
-- en treatment-service y appointment-service respectivamente). Solo uno de
-- los dos esta presente en cada fila (pago de tratamiento o pago de cita).
CREATE TABLE pagos (
    id INT AUTO_INCREMENT PRIMARY KEY,

    treatment_id INT NULL,

    appointment_id INT NULL,

    monto DECIMAL(10,2) NOT NULL,

    fecha DATETIME NOT NULL,

    metodo_pago ENUM('EFECTIVO', 'TARJETA', 'TRANSFERENCIA', 'YAPE_PLIN') NOT NULL
);
