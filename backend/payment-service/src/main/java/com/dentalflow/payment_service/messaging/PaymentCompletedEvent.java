package com.dentalflow.payment_service.messaging;

import java.math.BigDecimal;

public record PaymentCompletedEvent(
        Integer paymentId,
        String razon,
        Integer referenceId,
        BigDecimal amount
) {
}
