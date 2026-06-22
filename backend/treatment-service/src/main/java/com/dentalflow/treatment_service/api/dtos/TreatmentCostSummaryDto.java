package com.dentalflow.treatment_service.api.dtos;

import java.math.BigDecimal;

/**
 * Consumido via Feign por payment-service para validar montos antes de
 * registrar un pago, sin que payment-service necesite conocer las reglas de
 * negocio de tratamientos.
 */
public record TreatmentCostSummaryDto(
        Integer treatmentId,
        BigDecimal costoEstimado,
        BigDecimal montoPagado,
        BigDecimal montoPendiente
) {
}
