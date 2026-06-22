package com.dentalflow.payment_service.client.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AppointmentDto(
        Long id,
        Long patientId,
        String patientFullName,
        Long dentistId,
        String dentistFullName,
        BigDecimal amount
) {
}
