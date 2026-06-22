package com.dentalflow.appointment_service.domain.services;

import com.dentalflow.appointment_service.api.dtos.AppointmentTypeRequestDto;
import com.dentalflow.appointment_service.api.dtos.AppointmentTypeResponseDto;

import java.util.List;

public interface IAppointmentTypeService {

    AppointmentTypeResponseDto create(AppointmentTypeRequestDto request);

    List<AppointmentTypeResponseDto> getAll();

    AppointmentTypeResponseDto getById(Long id);
}
