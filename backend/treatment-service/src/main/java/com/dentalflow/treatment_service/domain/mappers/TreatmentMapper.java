package com.dentalflow.treatment_service.domain.mappers;

import com.dentalflow.treatment_service.api.dtos.TreatmentRequestDto;
import com.dentalflow.treatment_service.data.entities.TreatmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TreatmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", ignore = true)
    TreatmentEntity toEntity(TreatmentRequestDto dto);
}
