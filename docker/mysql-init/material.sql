CREATE DATABASE material_db;
USE material_db;

CREATE TABLE materiales (
    id INT AUTO_INCREMENT PRIMARY KEY,

    nombre VARCHAR(150) NOT NULL,

    stock INT NOT NULL,

    stock_minimo INT NOT NULL,

    costo_unitario DECIMAL(10,2) NOT NULL
);
