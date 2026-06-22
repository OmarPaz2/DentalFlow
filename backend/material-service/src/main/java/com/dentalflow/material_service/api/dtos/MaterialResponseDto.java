package com.dentalflow.material_service.api.dtos;

import java.math.BigDecimal;

public record MaterialResponseDto(
        Integer id,
        String nombre,
        Integer stock,
        Integer stockMinimo,
        BigDecimal costoUnitario,
        boolean stockCritico
) {
}
