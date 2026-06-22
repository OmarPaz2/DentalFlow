package com.dentalflow.treatment_service.client.dtos;

public record PatientDto(
        Long id,
        String dni,
        String firstName,
        String lastName
) {
}
