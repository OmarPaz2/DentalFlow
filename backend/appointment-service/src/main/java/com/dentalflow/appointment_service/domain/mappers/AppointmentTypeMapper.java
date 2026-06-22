package com.dentalflow.appointment_service.domain.mappers;

import com.dentalflow.appointment_service.api.dtos.AppointmentTypeRequestDto;
import com.dentalflow.appointment_service.api.dtos.AppointmentTypeResponseDto;
import com.dentalflow.appointment_service.data.entities.AppointmentTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentTypeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    AppointmentTypeEntity toEntity(AppointmentTypeRequestDto dto);

    AppointmentTypeResponseDto toResponseDto(AppointmentTypeEntity entity);

    List<AppointmentTypeResponseDto> toResponseDtoList(List<AppointmentTypeEntity> entities);
}
