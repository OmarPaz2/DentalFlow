package com.dentalflow.payment_service.client.dtos;

import java.math.BigDecimal;

public record TreatmentCostSummaryDto(
        Integer treatmentId,
        BigDecimal costoEstimado,
        BigDecimal montoPagado,
        BigDecimal montoPendiente
) {
}
