package com.dentalflow.dentist_service.domain.mappers;

import com.dentalflow.dentist_service.api.dtos.DentistRequestDto;
import com.dentalflow.dentist_service.api.dtos.DentistResponseDto;
import com.dentalflow.dentist_service.data.entities.DentistEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DentistMapper {

    DentistEntity toEntity(DentistRequestDto dto);

    DentistResponseDto toResponseDto(DentistEntity dentista);

    List<DentistResponseDto> toResponseDtoList(List<DentistEntity> dentistas);

}
