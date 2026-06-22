package com.dentalflow.dentist_service.api.dtos;

import com.dentalflow.dentist_service.data.entities.ClinicalStaffEntity.StaffType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ClinicalStaffRequestDto(
        @NotNull(message = "El id de usuario (auth-service) es requerido.")
        Long userId,

        /** Solo requerido cuando staffType = ODONTOLOGO. */
        Long specialtyId,

        /** Solo requerido cuando staffType = ODONTOLOGO. */
        @Size(max = 50)
        String licenseNumber,

        @NotBlank(message = "El nombre es requerido.")
        @Size(max = 100)
        String firstName,

        @NotBlank(message = "El apellido es requerido.")
        @Size(max = 100)
        String lastName,

        @Size(max = 20)
        String phone,

        @NotNull(message = "El tipo de personal es requerido.")
        StaffType staffType
) {
}
