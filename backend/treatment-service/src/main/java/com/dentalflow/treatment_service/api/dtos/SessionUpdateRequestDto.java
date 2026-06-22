package com.dentalflow.treatment_service.api.dtos;

import java.time.LocalDateTime;

public record SessionUpdateRequestDto(
        LocalDateTime fechaRealizada,
        String observaciones
) {
}
