package com.dentalflow.appointment_service.api.dtos;

import com.dentalflow.appointment_service.data.entities.AppointmentEntity.AppointmentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentResponseDto(
        Long id,
        Long patientId,
        String patientFullName,
        Long dentistId,
        String dentistFullName,
        Long appointmentTypeId,
        String appointmentTypeName,
        LocalDate appointmentDate,
        LocalTime startTime,
        LocalTime endTime,
        String reason,
        AppointmentStatus status,
        BigDecimal amount
) {
}
