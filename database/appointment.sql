CREATE DATABASE appointment_db;
CREATE TABLE appointment_types (
                                   id BIGSERIAL PRIMARY KEY,

                                   name VARCHAR(100) NOT NULL UNIQUE,

                                   duration_minutes INTEGER NOT NULL,

                                   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- patient_id y dentist_id son enteros planos, SIN foreign key:
-- el paciente vive en patient-service y el odontólogo en dentist-service.

CREATE TABLE citas (
                       id BIGSERIAL PRIMARY KEY,

                       patient_id INTEGER NOT NULL,

                       dentist_id INTEGER NOT NULL,

                       appointment_type_id BIGINT NOT NULL,

                       appointment_date DATE NOT NULL,

                       start_time TIME NOT NULL,

                       end_time TIME NOT NULL,

                       reason VARCHAR(255),

                       status VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE'
                           CHECK (
                               status IN (
                                          'PENDIENTE',
                                          'CONFIRMADA',
                                          'REPROGRAMADA',
                                          'CANCELADA',
                                          'COMPLETADA'
                                   )
                               ),

                       amount NUMERIC(10,2) NOT NULL,

                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                       CONSTRAINT fk_cita_appointment_type
                           FOREIGN KEY (appointment_type_id)
                               REFERENCES appointment_types(id)
);