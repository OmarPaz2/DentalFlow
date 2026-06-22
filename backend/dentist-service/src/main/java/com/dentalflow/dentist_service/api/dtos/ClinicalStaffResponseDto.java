package com.dentalflow.dentist_service.api.dtos;

import com.dentalflow.dentist_service.data.entities.ClinicalStaffEntity.StaffType;

public record ClinicalStaffResponseDto(
        Long id,
        Long userId,
        Long specialtyId,
        String specialtyName,
        String licenseNumber,
        String firstName,
        String lastName,
        String phone,
        StaffType staffType,
        Boolean available
) {
}
