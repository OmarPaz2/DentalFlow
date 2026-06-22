package com.dentalflow.material_service.domain.services.impls;

import com.dentalflow.material_service.api.dtos.MaterialRequestDto;
import com.dentalflow.material_service.api.dtos.MaterialResponseDto;
import com.dentalflow.material_service.api.exceptions.MaterialNotFoundException;
import com.dentalflow.material_service.data.entities.MaterialEntity;
import com.dentalflow.material_service.data.repositories.IMaterialRepository;
import com.dentalflow.material_service.domain.mappers.MaterialMapper;
import com.dentalflow.material_service.domain.services.IMaterialService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MaterialService implements IMaterialService {

    private final IMaterialRepository repository;
    private final MaterialMapper mapper;

    public MaterialService(IMaterialRepository repository, MaterialMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialResponseDto> materialGetAll() {
        return mapper.toResponseDtoList(repository.findAll());
    }

    @Override
    @Transactional
    public MaterialResponseDto materialCreate(MaterialRequestDto request) {
        MaterialEntity entity = mapper.toEntity(request);
        return mapper.toResponseDto(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public MaterialResponseDto materialGetById(Integer id) {
        MaterialEntity entity = repository.findById(id)
                .orElseThrow(() -> new MaterialNotFoundException(id));
        return mapper.toResponseDto(entity);
    }

    @Override
    @Transactional
    public MaterialResponseDto materialUpdate(Integer id, MaterialRequestDto request) {
        MaterialEntity existente = repository.findById(id)
                .orElseThrow(() -> new MaterialNotFoundException(id));

        existente.setNombre(request.nombre());
        existente.setStock(request.stock());
        existente.setStockMinimo(request.stockMinimo());
        existente.setCostoUnitario(request.costoUnitario());

        return mapper.toResponseDto(repository.save(existente));
    }

    @Override
    @Transactional
    public void materialDelete(Integer id) {
        if (!repository.existsById(id)) {
            throw new MaterialNotFoundException(id);
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialResponseDto> stockCritico() {
        return mapper.toResponseDtoList(
                repository.findAll()
                        .stream()
                        .filter(m -> m.getStock() <= m.getStockMinimo())
                        .toList()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public long stockCriticoCount() {
        return repository.findAll()
                .stream()
                .filter(m -> m.getStock() <= m.getStockMinimo())
                .count();
    }
}
