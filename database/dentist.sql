CREATE DATABASE dentist_db;
CREATE TABLE clinical_staff (
                                id BIGSERIAL PRIMARY KEY,

                                user_id BIGINT,

                                specialty_id BIGINT,

                                license_number VARCHAR(50) UNIQUE,

                                first_name VARCHAR(100) NOT NULL,

                                last_name VARCHAR(100) NOT NULL,

                                phone VARCHAR(20),

                                staff_type VARCHAR(20) NOT NULL
                                    CHECK (
                                        staff_type IN (
                                                       'ADMINISTRADOR',
                                                       'RECEPCIONISTA',
                                                       'ODONTOLOGO'
                                            )
                                        ),

                                available BOOLEAN DEFAULT TRUE,

                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
