package com.dentalflow.dentist_service.domain.services;

import com.dentalflow.dentist_service.api.dtos.DentistRequestDto;
import com.dentalflow.dentist_service.api.dtos.DentistResponseDto;

import java.util.List;

public interface IDentistService {

    void registrarDentista(DentistRequestDto requestDto);

    List<DentistResponseDto> obtenerTodosDentistas();

}
