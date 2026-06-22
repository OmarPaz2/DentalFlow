CREATE DATABASE auth_db;
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,

                       username VARCHAR(50) NOT NULL UNIQUE,

                       password VARCHAR(255) NOT NULL,

                       role VARCHAR(20) NOT NULL
                           CHECK (
                               role IN (
                                        'ADMIN',
                                        'RECEPCIONISTA',
                                        'ODONTOLOGO'
                                   )
                               ),

                       active BOOLEAN DEFAULT TRUE,

                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);