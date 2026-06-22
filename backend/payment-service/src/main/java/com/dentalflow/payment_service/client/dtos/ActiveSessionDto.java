package com.dentalflow.payment_service.client.dtos;

import java.math.BigDecimal;

public record ActiveSessionDto(
        Integer sessionId,
        BigDecimal costoParcial
) {
}
