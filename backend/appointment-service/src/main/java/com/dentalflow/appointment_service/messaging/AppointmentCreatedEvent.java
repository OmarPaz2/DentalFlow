package com.dentalflow.appointment_service.messaging;

import java.time.LocalDate;

public record AppointmentCreatedEvent(
        Long appointmentId,
        Long patientId,
        Long dentistId,
        LocalDate appointmentDate
) {
}
