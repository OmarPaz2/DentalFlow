package com.dentalflow.payment_service.api.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PaymentRequestDto(
        @NotNull(message = "El monto es requerido.")
        @Positive(message = "El monto debe ser mayor a cero.")
        BigDecimal monto,

        @NotNull(message = "El metodo de pago es requerido.")
        String metodoPago
) {
}
