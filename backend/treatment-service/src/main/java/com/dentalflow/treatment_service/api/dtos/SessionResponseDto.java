package com.dentalflow.treatment_service.api.dtos;

import com.dentalflow.treatment_service.data.entities.TreatmentSessionEntity.EstadoSesion;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SessionResponseDto(
        Integer id,
        Integer treatmentId,
        String patientFirstName,
        String patientLastName,
        String dni,
        LocalDateTime fechaProgramada,
        LocalDateTime fechaRealizada,
        BigDecimal costoParcial,
        String observaciones,
        EstadoSesion estado
) {
}
