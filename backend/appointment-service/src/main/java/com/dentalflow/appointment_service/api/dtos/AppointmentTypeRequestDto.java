package com.dentalflow.appointment_service.api.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AppointmentTypeRequestDto(
        @NotBlank(message = "El nombre del tipo de cita es requerido.")
        String name,

        @NotNull(message = "La duracion en minutos es requerida.")
        @Min(value = 5, message = "La duracion minima es 5 minutos.")
        Integer durationMinutes
) {
}
