package com.dentalflow.dentist_service.domain.services.impls;

import com.dentalflow.dentist_service.api.dtos.DentistRequestDto;
import com.dentalflow.dentist_service.api.dtos.DentistResponseDto;
import com.dentalflow.dentist_service.data.repositories.IDentistRepository;
import com.dentalflow.dentist_service.domain.mappers.DentistMapper;
import com.dentalflow.dentist_service.domain.services.IDentistService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DentistService implements IDentistService {

    private final IDentistRepository _dentistRepo;
    private final DentistMapper _dentistMapper;

    public DentistService(IDentistRepository dentistRepo, DentistMapper dentistMapper){
        this._dentistRepo = dentistRepo;
        this._dentistMapper = dentistMapper;
    }

    @Override
    public void registrarDentista(DentistRequestDto requestDto) {
        _dentistRepo.save(_dentistMapper.toEntity(requestDto));
    }

    @Override
    public List<DentistResponseDto> obtenerTodosDentistas() {
        return _dentistMapper.toResponseDtoList(_dentistRepo.findAll());
    }
}
