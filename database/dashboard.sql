CREATE DATABASE dashboard_db;
CREATE TABLE dashboard_metrics (
                                   id BIGINT PRIMARY KEY,

                                   citas_hoy INTEGER NOT NULL DEFAULT 0,

                                   tratamientos_completados INTEGER NOT NULL DEFAULT 0,

                                   pagos_realizados INTEGER NOT NULL DEFAULT 0,

                                   monto_total_pagos NUMERIC(12,2) NOT NULL DEFAULT 0,

                                   materiales_stock_critico_cache INTEGER NOT NULL DEFAULT 0,

                                   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
