package com.dentalflow.dashboard_service.api.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DashboardMetricsResponseDto(
        Integer citasHoy,
        Integer tratamientosCompletados,
        Integer pagosRealizados,
        BigDecimal montoTotalPagos,
        Integer materialesStockCritico,
        LocalDateTime updatedAt
) {
}
