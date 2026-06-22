package com.dentalflow.appointment_service.api.dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record RescheduleRequestDto(
        @NotNull(message = "La nueva fecha es requerida.")
        @Future(message = "La nueva fecha debe ser futura.")
        LocalDate appointmentDate,

        @NotNull(message = "La nueva hora de inicio es requerida.")
        LocalTime startTime
) {
}
