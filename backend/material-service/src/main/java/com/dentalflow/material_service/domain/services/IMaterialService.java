package com.dentalflow.material_service.domain.services;

import com.dentalflow.material_service.api.dtos.MaterialRequestDto;
import com.dentalflow.material_service.api.dtos.MaterialResponseDto;

import java.util.List;

public interface IMaterialService {

    List<MaterialResponseDto> materialGetAll();

    MaterialResponseDto materialCreate(MaterialRequestDto request);

    MaterialResponseDto materialGetById(Integer id);

    MaterialResponseDto materialUpdate(Integer id, MaterialRequestDto request);

    void materialDelete(Integer id);

    List<MaterialResponseDto> stockCritico();

    long stockCriticoCount();
}
