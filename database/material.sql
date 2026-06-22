CREATE DATABASE material_db;
USE material_db;

CREATE TABLE materiales (
                            id SERIAL PRIMARY KEY,

                            nombre VARCHAR(150) NOT NULL,

                            stock INTEGER NOT NULL,

                            stock_minimo INTEGER NOT NULL,

                            costo_unitario NUMERIC(10,2) NOT NULL
);
