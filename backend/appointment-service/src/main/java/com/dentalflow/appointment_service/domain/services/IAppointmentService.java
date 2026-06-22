package com.dentalflow.appointment_service.domain.services;

import com.dentalflow.appointment_service.api.dtos.AppointmentRequestDto;
import com.dentalflow.appointment_service.api.dtos.AppointmentResponseDto;
import com.dentalflow.appointment_service.api.dtos.RescheduleRequestDto;

import java.util.List;

public interface IAppointmentService {

    AppointmentResponseDto create(AppointmentRequestDto request);

    AppointmentResponseDto getById(Long id);

    List<AppointmentResponseDto> getByPatient(Long patientId);

    List<AppointmentResponseDto> getByDentist(Long dentistId);

    AppointmentResponseDto reschedule(Long id, RescheduleRequestDto request);

    AppointmentResponseDto cancel(Long id);
}
