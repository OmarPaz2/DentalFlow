package com.dentalflow.payment_service.client.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TreatmentDto(
        Integer id,
        Integer patientId,
        String patientFirstName,
        String patientLastName,
        Integer dentistId,
        BigDecimal costoEstimado
) {
}
