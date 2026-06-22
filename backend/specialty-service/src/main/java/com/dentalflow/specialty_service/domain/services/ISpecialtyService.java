package com.dentalflow.specialty_service.domain.services;

import com.dentalflow.specialty_service.api.dtos.SpecialtyRequestDto;
import com.dentalflow.specialty_service.api.dtos.SpecialtyResponseDto;

import java.util.List;

public interface ISpecialtyService {

    SpecialtyResponseDto createSpecialty(SpecialtyRequestDto request);

    SpecialtyResponseDto updateSpecialty(Long id, SpecialtyRequestDto request);

    SpecialtyResponseDto getById(Long id);

    List<SpecialtyResponseDto> getAllSpecialties();
}
