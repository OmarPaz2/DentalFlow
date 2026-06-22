package com.dentalflow.specialty_service.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SpecialtyRequestDto(
        @NotBlank(message = "El nombre de la especialidad es requerido.")
        @Size(max = 100, message = "El nombre debe tener menos de 100 caracteres.")
        String name
) {
}
