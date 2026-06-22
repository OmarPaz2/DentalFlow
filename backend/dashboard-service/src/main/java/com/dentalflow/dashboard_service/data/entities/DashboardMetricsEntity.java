package com.dentalflow.dashboard_service.data.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Tabla de una sola fila (id=1) que acumula contadores operativos. Se
 * actualiza de forma asincrona via RabbitMQ (ver messaging/*Listener), no
 * mediante llamadas sincronas a cada microservicio en cada request del
 * dashboard - asi el dashboard sigue respondiendo rapido aunque algun
 * microservicio este lento.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dashboard_metrics")
@Entity
public class DashboardMetricsEntity {

    @Id
    private Long id;

    @Column(name = "citas_hoy", nullable = false)
    @Builder.Default
    private Integer citasHoy = 0;

    @Column(name = "tratamientos_completados", nullable = false)
    @Builder.Default
    private Integer tratamientosCompletados = 0;

    @Column(name = "pagos_realizados", nullable = false)
    @Builder.Default
    private Integer pagosRealizados = 0;

    @Column(name = "monto_total_pagos", nullable = false, precision = 12, scale = 2)
    @Builder.Default
    private BigDecimal montoTotalPagos = BigDecimal.ZERO;

    @Column(name = "materiales_stock_critico_cache", nullable = false)
    @Builder.Default
    private Integer materialesStockCriticoCache = 0;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    protected void onSave() {
        this.updatedAt = LocalDateTime.now();
    }
}
