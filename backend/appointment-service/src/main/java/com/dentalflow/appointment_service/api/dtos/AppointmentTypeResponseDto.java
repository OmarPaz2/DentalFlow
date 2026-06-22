package com.dentalflow.appointment_service.api.dtos;

public record AppointmentTypeResponseDto(
        Long id,
        String name,
        Integer durationMinutes
) {
}
