package com.dentalflow.dentist_service.domain.mappers;

import com.dentalflow.dentist_service.api.dtos.ClinicalStaffRequestDto;
import com.dentalflow.dentist_service.api.dtos.ClinicalStaffResponseDto;
import com.dentalflow.dentist_service.data.entities.ClinicalStaffEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClinicalStaffMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "available", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ClinicalStaffEntity toEntity(ClinicalStaffRequestDto dto);

    @Mapping(target = "specialtyName", ignore = true)
    ClinicalStaffResponseDto toResponseDto(ClinicalStaffEntity entity);
}
