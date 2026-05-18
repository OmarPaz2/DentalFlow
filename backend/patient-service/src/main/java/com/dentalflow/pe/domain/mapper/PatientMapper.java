package com.dentalflow.pe.domain.mapper;

import com.dentalflow.pe.api.dto.PatientRequestDto;
import com.dentalflow.pe.api.dto.PatientResponseDto;
import com.dentalflow.pe.data.entity.Gender;
import com.dentalflow.pe.data.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "gender", source = "gender", qualifiedByName = "stringToGender")
    Patient toEntity(PatientRequestDto dto);

    @Mapping(target = "gender", source = "gender", qualifiedByName = "genderToString")
    PatientResponseDto toResponse(Patient entity);

    @Named("stringToGender")
    default Gender stringToGender(String gender) {
        return gender == null ? null : Gender.valueOf(gender);
    }

    @Named("genderToString")
    default String genderToString(Gender gender) {
        return gender == null ? null : gender.name();
    }
}
