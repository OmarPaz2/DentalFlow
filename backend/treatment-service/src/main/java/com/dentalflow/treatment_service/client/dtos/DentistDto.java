package com.dentalflow.treatment_service.client.dtos;

public record DentistDto(
        Long id,
        String firstName,
        String lastName,
        String staffType
) {
}
