package com.dentalflow.specialty_service.domain.services.impls;

import com.dentalflow.specialty_service.api.dtos.SpecialtyRequestDto;
import com.dentalflow.specialty_service.api.dtos.SpecialtyResponseDto;
import com.dentalflow.specialty_service.api.exceptions.SpecialtyAlreadyExistsException;
import com.dentalflow.specialty_service.api.exceptions.SpecialtyNotFoundException;
import com.dentalflow.specialty_service.data.entities.SpecialtyEntity;
import com.dentalflow.specialty_service.data.repositories.ISpecialtyRepository;
import com.dentalflow.specialty_service.domain.mappers.SpecialtyMapper;
import com.dentalflow.specialty_service.domain.services.ISpecialtyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SpecialtyService implements ISpecialtyService {

    private final ISpecialtyRepository repository;
    private final SpecialtyMapper mapper;

    public SpecialtyService(ISpecialtyRepository repository, SpecialtyMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public SpecialtyResponseDto createSpecialty(SpecialtyRequestDto request) {
        repository.findByName(request.name()).ifPresent(existing -> {
            throw new SpecialtyAlreadyExistsException(request.name());
        });

        SpecialtyEntity entity = mapper.toEntity(request);
        return mapper.toResponseDto(repository.save(entity));
    }

    @Override
    @Transactional
    public SpecialtyResponseDto updateSpecialty(Long id, SpecialtyRequestDto request) {
        SpecialtyEntity entity = repository.findById(id)
                .orElseThrow(() -> new SpecialtyNotFoundException(id));

        repository.findByName(request.name()).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new SpecialtyAlreadyExistsException(request.name());
            }
        });

        entity.setName(request.name());
        return mapper.toResponseDto(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public SpecialtyResponseDto getById(Long id) {
        SpecialtyEntity entity = repository.findById(id)
                .orElseThrow(() -> new SpecialtyNotFoundException(id));
        return mapper.toResponseDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpecialtyResponseDto> getAllSpecialties() {
        return mapper.toResponseDtoList(repository.findAll());
    }
}
