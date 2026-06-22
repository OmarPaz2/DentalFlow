package com.dentalflow.treatment_service.api.dtos;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TreatmentRequestDto(
        @NotNull(message = "El paciente es requerido.")
        Long patientId,

        @NotNull(message = "El odontologo es requerido.")
        Long dentistId,

        @NotBlank(message = "El diagnostico es requerido.")
        String diagnostico,

        @NotBlank(message = "El tipo de tratamiento es requerido.")
        @Size(max = 100)
        String tipoTratamiento,

        @NotNull(message = "El costo estimado es requerido.")
        @Positive(message = "El costo estimado debe ser mayor a cero.")
        BigDecimal costoEstimado,

        @NotNull(message = "La fecha de inicio es requerida.")
        LocalDate fechaInicio,

        @NotNull(message = "La cantidad de sesiones es requerida.")
        @Min(value = 1, message = "Debe haber al menos 1 sesion.")
        Integer cantSesiones
) {
}
