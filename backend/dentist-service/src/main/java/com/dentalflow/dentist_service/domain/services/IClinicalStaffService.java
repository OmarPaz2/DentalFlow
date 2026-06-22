package com.dentalflow.dentist_service.domain.services;

import com.dentalflow.dentist_service.api.dtos.ClinicalStaffRequestDto;
import com.dentalflow.dentist_service.api.dtos.ClinicalStaffResponseDto;
import com.dentalflow.dentist_service.data.entities.ClinicalStaffEntity.StaffType;

import java.util.List;

public interface IClinicalStaffService {

    ClinicalStaffResponseDto registrar(ClinicalStaffRequestDto request);

    List<ClinicalStaffResponseDto> obtenerTodos();

    ClinicalStaffResponseDto obtenerPorId(Long id);

    List<ClinicalStaffResponseDto> obtenerPorTipo(StaffType staffType);

    ClinicalStaffResponseDto actualizarDisponibilidad(Long id, boolean available);
}
