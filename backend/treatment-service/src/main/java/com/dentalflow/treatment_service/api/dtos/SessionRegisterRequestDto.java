package com.dentalflow.treatment_service.api.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SessionRegisterRequestDto(
        @NotNull(message = "El id del tratamiento es requerido.")
        Integer treatmentId,

        LocalDateTime fechaProgramada,

        @NotNull(message = "El costo parcial es requerido.")
        @PositiveOrZero(message = "El costo parcial no puede ser negativo.")
        BigDecimal costoParcial
) {
}
