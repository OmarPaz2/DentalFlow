package com.dentalflow.dentist_service.domain.services.impls;

import com.dentalflow.dentist_service.api.dtos.ClinicalStaffRequestDto;
import com.dentalflow.dentist_service.api.dtos.ClinicalStaffResponseDto;
import com.dentalflow.dentist_service.api.exceptions.ClinicalStaffNotFoundException;
import com.dentalflow.dentist_service.api.exceptions.InvalidStaffDataException;
import com.dentalflow.dentist_service.api.exceptions.LicenseAlreadyExistsException;
import com.dentalflow.dentist_service.client.SpecialtyClient;
import com.dentalflow.dentist_service.client.dtos.SpecialtyDto;
import com.dentalflow.dentist_service.data.entities.ClinicalStaffEntity;
import com.dentalflow.dentist_service.data.entities.ClinicalStaffEntity.StaffType;
import com.dentalflow.dentist_service.data.repositories.IClinicalStaffRepository;
import com.dentalflow.dentist_service.domain.mappers.ClinicalStaffMapper;
import com.dentalflow.dentist_service.domain.services.IClinicalStaffService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ClinicalStaffService implements IClinicalStaffService {

    private final IClinicalStaffRepository repository;
    private final ClinicalStaffMapper mapper;
    private final SpecialtyClient specialtyClient;

    public ClinicalStaffService(IClinicalStaffRepository repository,
                                 ClinicalStaffMapper mapper,
                                 SpecialtyClient specialtyClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.specialtyClient = specialtyClient;
    }

    @Override
    @Transactional
    public ClinicalStaffResponseDto registrar(ClinicalStaffRequestDto request) {
        validarDatosPorTipo(request);

        if (StringUtils.hasText(request.licenseNumber())) {
            repository.findByLicenseNumber(request.licenseNumber()).ifPresent(existing -> {
                throw new LicenseAlreadyExistsException(request.licenseNumber());
            });
        }

        ClinicalStaffEntity entity = mapper.toEntity(request);
        ClinicalStaffEntity saved = repository.save(entity);
        return toResponseWithSpecialty(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClinicalStaffResponseDto> obtenerTodos() {
        return repository.findAll()
                .stream()
                .map(this::toResponseWithSpecialty)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClinicalStaffResponseDto obtenerPorId(Long id) {
        ClinicalStaffEntity entity = repository.findById(id)
                .orElseThrow(() -> new ClinicalStaffNotFoundException(id));
        return toResponseWithSpecialty(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClinicalStaffResponseDto> obtenerPorTipo(StaffType staffType) {
        return repository.findByStaffType(staffType)
                .stream()
                .map(this::toResponseWithSpecialty)
                .toList();
    }

    @Override
    @Transactional
    public ClinicalStaffResponseDto actualizarDisponibilidad(Long id, boolean available) {
        ClinicalStaffEntity entity = repository.findById(id)
                .orElseThrow(() -> new ClinicalStaffNotFoundException(id));
        entity.setAvailable(available);
        return toResponseWithSpecialty(repository.save(entity));
    }

    /**
     * Solo los odontologos requieren especialidad y numero de colegiatura,
     * igual que en la version SOAP original.
     */
    private void validarDatosPorTipo(ClinicalStaffRequestDto request) {
        if (request.staffType() == StaffType.ODONTOLOGO) {
            if (request.specialtyId() == null) {
                throw new InvalidStaffDataException("La especialidad es requerida para personal de tipo ODONTOLOGO.");
            }
            if (!StringUtils.hasText(request.licenseNumber())) {
                throw new InvalidStaffDataException("El numero de colegiatura es requerido para personal de tipo ODONTOLOGO.");
            }
        }
    }

    /**
     * Resuelve el nombre de la especialidad llamando a specialty-service via
     * Feign. La llamada esta protegida por CircuitBreaker (ver application.yaml):
     * si specialty-service esta caido, SpecialtyClientFallback evita que
     * falle toda la consulta de personal clinico.
     */
    private ClinicalStaffResponseDto toResponseWithSpecialty(ClinicalStaffEntity entity) {
        ClinicalStaffResponseDto base = mapper.toResponseDto(entity);
        String specialtyName = null;
        if (entity.getSpecialtyId() != null) {
            SpecialtyDto specialty = specialtyClient.getById(entity.getSpecialtyId());
            specialtyName = specialty != null ? specialty.name() : null;
        }
        return new ClinicalStaffResponseDto(
                base.id(), base.userId(), base.specialtyId(), specialtyName,
                base.licenseNumber(), base.firstName(), base.lastName(), base.phone(),
                base.staffType(), base.available()
        );
    }
}
