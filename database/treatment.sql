CREATE DATABASE treatment_db;
CREATE TYPE estado_tratamiento_enum AS ENUM (
    'PLANIFICADO',
    'EN_PROGRESO',
    'COMPLETADA',
    'INTERRUMPIDO'
);

CREATE TYPE estado_sesion_enum AS ENUM (
    'PROGRAMADA',
    'REALIZADA',
    'CANCELADA'
);

CREATE TABLE tratamientos (
                              id BIGSERIAL PRIMARY KEY,

                              patient_id INTEGER NOT NULL,

                              dentist_id INTEGER NOT NULL,

                              diagnostico TEXT NOT NULL,

                              tipo_tratamiento VARCHAR(100) NOT NULL,

                              costo_estimado NUMERIC(10,2) NOT NULL,

                              fecha_inicio DATE NOT NULL,

                              cant_sesiones INTEGER NOT NULL,

                              estado estado_tratamiento_enum NOT NULL DEFAULT 'PLANIFICADO'
);

CREATE TABLE sesiones_tratamiento (
                                      id BIGSERIAL PRIMARY KEY,

                                      treatment_id BIGINT NOT NULL,

                                      fecha_programada TIMESTAMP,

                                      fecha_realizada TIMESTAMP,

                                      observaciones TEXT,

                                      costo_parcial NUMERIC(10,2) NOT NULL DEFAULT 0,

                                      estado estado_sesion_enum NOT NULL DEFAULT 'PROGRAMADA',

                                      CONSTRAINT fk_sesion_tratamiento
                                          FOREIGN KEY (treatment_id)
                                              REFERENCES tratamientos(id)
                                              ON DELETE CASCADE
);