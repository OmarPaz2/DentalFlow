package com.dentalflow.appointment_service.api.dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentRequestDto(
        @NotNull(message = "El paciente es requerido.")
        Long patientId,

        @NotNull(message = "El personal clinico (odontologo) es requerido.")
        Long dentistId,

        @NotNull(message = "El tipo de cita es requerido.")
        Long appointmentTypeId,

        @NotNull(message = "La fecha de la cita es requerida.")
        @Future(message = "La fecha de la cita debe ser futura.")
        LocalDate appointmentDate,

        @NotNull(message = "La hora de inicio es requerida.")
        LocalTime startTime,

        String reason,

        @NotNull(message = "El monto es requerido.")
        @Positive(message = "El monto debe ser mayor a cero.")
        BigDecimal amount
) {
}
