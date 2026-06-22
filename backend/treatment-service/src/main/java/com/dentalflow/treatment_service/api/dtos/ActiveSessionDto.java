package com.dentalflow.treatment_service.api.dtos;

import java.math.BigDecimal;

public record ActiveSessionDto(
        Integer sessionId,
        BigDecimal costoParcial
) {
}
