CREATE DATABASE dashboard_db;
USE dashboard_db;

-- Tabla de una sola fila (id=1): contadores operativos que dashboard-service
-- actualiza de forma asincrona al escuchar eventos de RabbitMQ publicados
-- por appointment-service, treatment-service y payment-service.
CREATE TABLE dashboard_metrics (
    id BIGINT PRIMARY KEY,

    citas_hoy INT NOT NULL DEFAULT 0,

    tratamientos_completados INT NOT NULL DEFAULT 0,

    pagos_realizados INT NOT NULL DEFAULT 0,

    monto_total_pagos DECIMAL(12,2) NOT NULL DEFAULT 0,

    materiales_stock_critico_cache INT NOT NULL DEFAULT 0,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
