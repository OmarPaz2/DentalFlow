package com.dentalflow.material_service.api.dtos;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record MaterialRequestDto(
        @NotBlank(message = "El nombre del material es requerido.")
        @Size(max = 150)
        String nombre,

        @NotNull(message = "El stock es requerido.")
        @PositiveOrZero(message = "El stock no puede ser negativo.")
        Integer stock,

        @NotNull(message = "El stock minimo es requerido.")
        @PositiveOrZero(message = "El stock minimo no puede ser negativo.")
        Integer stockMinimo,

        @NotNull(message = "El costo unitario es requerido.")
        @PositiveOrZero(message = "El costo unitario no puede ser negativo.")
        BigDecimal costoUnitario
) {
}
