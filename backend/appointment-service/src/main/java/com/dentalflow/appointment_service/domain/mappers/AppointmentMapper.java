package com.dentalflow.appointment_service.domain.mappers;

import com.dentalflow.appointment_service.api.dtos.AppointmentRequestDto;
import com.dentalflow.appointment_service.api.dtos.AppointmentResponseDto;
import com.dentalflow.appointment_service.data.entities.AppointmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "patientId", source = "patientId")
    @Mapping(target = "dentistId", source = "dentistId")
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "endTime", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    AppointmentEntity toEntity(AppointmentRequestDto dto);

    @Mapping(target = "patientFullName", ignore = true)
    @Mapping(target = "dentistFullName", ignore = true)
    @Mapping(target = "appointmentTypeName", ignore = true)
    AppointmentResponseDto toResponseDto(AppointmentEntity entity);
}
