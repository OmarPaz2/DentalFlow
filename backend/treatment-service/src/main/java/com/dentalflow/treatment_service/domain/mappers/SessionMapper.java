package com.dentalflow.treatment_service.domain.mappers;

import com.dentalflow.treatment_service.api.dtos.SessionRegisterRequestDto;
import com.dentalflow.treatment_service.data.entities.TreatmentSessionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SessionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "fechaRealizada", ignore = true)
    @Mapping(target = "observaciones", ignore = true)
    TreatmentSessionEntity toEntity(SessionRegisterRequestDto dto);
}
