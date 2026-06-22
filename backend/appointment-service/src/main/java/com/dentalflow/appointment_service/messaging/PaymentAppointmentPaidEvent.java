package com.dentalflow.appointment_service.messaging;

import java.math.BigDecimal;

public record PaymentAppointmentPaidEvent(
        Long paymentId,
        Long appointmentId,
        BigDecimal amount
) {
}
