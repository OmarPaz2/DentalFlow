package com.dentalflow.specialty_service.api.dtos;

import java.time.LocalDateTime;

public record SpecialtyResponseDto(
        Long id,
        String name,
        LocalDateTime createdAt
) {
}
