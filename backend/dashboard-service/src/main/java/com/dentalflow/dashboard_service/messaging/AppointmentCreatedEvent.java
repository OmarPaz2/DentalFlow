package com.dentalflow.dashboard_service.messaging;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AppointmentCreatedEvent(
        Long appointmentId,
        Long patientId,
        Long dentistId
) {
}
