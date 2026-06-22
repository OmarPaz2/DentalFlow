package com.dentalflow.appointment_service.domain.services.impls;

import com.dentalflow.appointment_service.api.dtos.AppointmentTypeRequestDto;
import com.dentalflow.appointment_service.api.dtos.AppointmentTypeResponseDto;
import com.dentalflow.appointment_service.api.exceptions.AppointmentTypeNotFoundException;
import com.dentalflow.appointment_service.data.entities.AppointmentTypeEntity;
import com.dentalflow.appointment_service.data.repositories.IAppointmentTypeRepository;
import com.dentalflow.appointment_service.domain.mappers.AppointmentTypeMapper;
import com.dentalflow.appointment_service.domain.services.IAppointmentTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppointmentTypeService implements IAppointmentTypeService {

    private final IAppointmentTypeRepository repository;
    private final AppointmentTypeMapper mapper;

    public AppointmentTypeService(IAppointmentTypeRepository repository, AppointmentTypeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public AppointmentTypeResponseDto create(AppointmentTypeRequestDto request) {
        AppointmentTypeEntity entity = mapper.toEntity(request);
        return mapper.toResponseDto(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentTypeResponseDto> getAll() {
        return mapper.toResponseDtoList(repository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentTypeResponseDto getById(Long id) {
        AppointmentTypeEntity entity = repository.findById(id)
                .orElseThrow(() -> new AppointmentTypeNotFoundException(id));
        return mapper.toResponseDto(entity);
    }
}
