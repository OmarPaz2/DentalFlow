package com.dentalflow.specialty_service.domain.mappers;

import com.dentalflow.specialty_service.api.dtos.SpecialtyRequestDto;
import com.dentalflow.specialty_service.api.dtos.SpecialtyResponseDto;
import com.dentalflow.specialty_service.data.entities.SpecialtyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpecialtyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    SpecialtyEntity toEntity(SpecialtyRequestDto dto);

    SpecialtyResponseDto toResponseDto(SpecialtyEntity entity);

    List<SpecialtyResponseDto> toResponseDtoList(List<SpecialtyEntity> entities);
}
