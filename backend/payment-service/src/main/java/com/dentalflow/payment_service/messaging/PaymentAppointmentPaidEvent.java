package com.dentalflow.payment_service.messaging;

import java.math.BigDecimal;

public record PaymentAppointmentPaidEvent(
        Integer paymentId,
        Long appointmentId,
        BigDecimal amount
) {
}
