package com.dentalflow.material_service.domain.mappers;

import com.dentalflow.material_service.api.dtos.MaterialRequestDto;
import com.dentalflow.material_service.api.dtos.MaterialResponseDto;
import com.dentalflow.material_service.data.entities.MaterialEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MaterialMapper {

    @Mapping(target = "id", ignore = true)
    MaterialEntity toEntity(MaterialRequestDto dto);

    @Mapping(target = "stockCritico", expression = "java(entity.getStock() <= entity.getStockMinimo())")
    MaterialResponseDto toResponseDto(MaterialEntity entity);

    List<MaterialResponseDto> toResponseDtoList(List<MaterialEntity> entities);
}
