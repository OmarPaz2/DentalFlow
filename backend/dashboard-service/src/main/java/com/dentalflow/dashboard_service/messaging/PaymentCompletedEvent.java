package com.dentalflow.dashboard_service.messaging;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PaymentCompletedEvent(
        Integer paymentId,
        String razon,
        Integer referenceId,
        BigDecimal amount
) {
}
