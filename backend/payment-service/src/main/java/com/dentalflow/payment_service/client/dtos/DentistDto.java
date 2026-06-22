package com.dentalflow.payment_service.client.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DentistDto(
        Long id,
        String firstName,
        String lastName,
        String specialtyName
) {
}
