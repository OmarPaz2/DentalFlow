package com.dentalflow.dashboard_service.domain.services;

import com.dentalflow.dashboard_service.api.dtos.DashboardMetricsResponseDto;

import java.math.BigDecimal;

public interface IDashboardService {

    DashboardMetricsResponseDto getMetrics();

    void incrementCitasHoy();

    void incrementTratamientosCompletados();

    void registrarPago(BigDecimal monto);
}
