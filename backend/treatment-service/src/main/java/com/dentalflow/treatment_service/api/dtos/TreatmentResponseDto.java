package com.dentalflow.treatment_service.api.dtos;

import com.dentalflow.treatment_service.data.entities.TreatmentEntity.EstadoTratamiento;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TreatmentResponseDto(
        Integer id,
        Integer patientId,
        String patientFirstName,
        String patientLastName,
        String dni,
        Integer dentistId,
        String dentistFirstName,
        String dentistLastName,
        String diagnostico,
        String tipoTratamiento,
        BigDecimal costoEstimado,
        LocalDate fechaInicio,
        Integer cantSesiones,
        Integer sesionesRestantes,
        BigDecimal montoPagado,
        EstadoTratamiento estado
) {
}
