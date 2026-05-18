package com.dentalflow.dentist_service.api.dtos;

import jakarta.validation.constraints.*;

public record DentistRequestDto (
        @NotBlank(message = "Nro. Licencia es requerida.")
        @Size(max = 50, message = "Nro. Licencia debe tener menos de 50 caracteres.")
        String nroLicencia,
        @NotBlank(message = "Los Nombres son requeridos.")
        String nombre,
        @NotBlank(message = "Los Apellidos son requeridos.")
        String apellido,
        @Size(max = 50, message = "El telefono debe tener menos de 20 digitos.")
        String telefono,
        @NotNull(message = "La especialidad es requerida.")
        Long idEspecialidad
){
}