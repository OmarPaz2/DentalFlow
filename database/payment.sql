CREATE DATABASE payment_db;
CREATE TYPE metodo_pago_enum AS ENUM (
    'EFECTIVO',
    'TARJETA',
    'TRANSFERENCIA',
    'YAPE_PLIN'
);

CREATE TABLE pagos (
                       id BIGSERIAL PRIMARY KEY,

                       treatment_id INTEGER NULL,

                       appointment_id INTEGER NULL,

                       monto NUMERIC(10,2) NOT NULL,

                       fecha TIMESTAMP NOT NULL,

                       metodo_pago metodo_pago_enum NOT NULL
);