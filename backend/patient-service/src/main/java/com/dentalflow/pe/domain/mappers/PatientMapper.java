package com.dentalflow.pe.domain.mappers;

import com.dentalflow.pe.api.dto.PatientRequestDto;
import com.dentalflow.pe.data.entity.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    Patient toEntity(PatientRequestDto patientDto);
}
