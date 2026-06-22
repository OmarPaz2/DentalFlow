package com.dentalflow.treatment_service.client.dtos;

import java.util.List;

public record PatientSearchResponseDto(
        String message,
        List<PatientDto> data
) {
}
