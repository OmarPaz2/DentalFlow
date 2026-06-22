package com.dentalflow.payment_service.api.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponseDto(
        Integer idPago,
        String razon,
        String nombresPaciente,
        String apellidosPaciente,
        String nombreEspecialidad,
        BigDecimal monto,
        LocalDateTime fecha,
        String metodoPago
) {
}
