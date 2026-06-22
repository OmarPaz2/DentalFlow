package com.dentalflow.appointment_service.client.dtos;

public record DentistDto(
        Long id,
        String firstName,
        String lastName,
        String staffType,
        Boolean available
) {
}
