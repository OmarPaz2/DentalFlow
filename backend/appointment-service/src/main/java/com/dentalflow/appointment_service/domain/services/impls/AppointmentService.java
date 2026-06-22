package com.dentalflow.appointment_service.domain.services.impls;

import com.dentalflow.appointment_service.api.dtos.AppointmentRequestDto;
import com.dentalflow.appointment_service.api.dtos.AppointmentResponseDto;
import com.dentalflow.appointment_service.api.dtos.RescheduleRequestDto;
import com.dentalflow.appointment_service.api.exceptions.AppointmentNotFoundException;
import com.dentalflow.appointment_service.api.exceptions.AppointmentTypeNotFoundException;
import com.dentalflow.appointment_service.api.exceptions.InvalidAppointmentException;
import com.dentalflow.appointment_service.client.DentistClient;
import com.dentalflow.appointment_service.client.PatientClient;
import com.dentalflow.appointment_service.client.dtos.DentistDto;
import com.dentalflow.appointment_service.client.dtos.PatientDto;
import com.dentalflow.appointment_service.data.entities.AppointmentEntity;
import com.dentalflow.appointment_service.data.entities.AppointmentTypeEntity;
import com.dentalflow.appointment_service.data.repositories.IAppointmentRepository;
import com.dentalflow.appointment_service.data.repositories.IAppointmentTypeRepository;
import com.dentalflow.appointment_service.domain.mappers.AppointmentMapper;
import com.dentalflow.appointment_service.domain.services.IAppointmentService;
import com.dentalflow.appointment_service.messaging.AppointmentCreatedEvent;
import com.dentalflow.appointment_service.messaging.AppointmentEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppointmentService implements IAppointmentService {

    private final IAppointmentRepository repository;
    private final IAppointmentTypeRepository appointmentTypeRepository;
    private final AppointmentMapper mapper;
    private final PatientClient patientClient;
    private final DentistClient dentistClient;
    private final AppointmentEventPublisher eventPublisher;

    public AppointmentService(IAppointmentRepository repository,
                               IAppointmentTypeRepository appointmentTypeRepository,
                               AppointmentMapper mapper,
                               PatientClient patientClient,
                               DentistClient dentistClient,
                               AppointmentEventPublisher eventPublisher) {
        this.repository = repository;
        this.appointmentTypeRepository = appointmentTypeRepository;
        this.mapper = mapper;
        this.patientClient = patientClient;
        this.dentistClient = dentistClient;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public AppointmentResponseDto create(AppointmentRequestDto request) {
        // Validaciones sincronas via Feign (protegidas por CircuitBreaker, ver application.yaml)
        PatientDto patient = patientClient.getById(request.patientId());
        DentistDto dentist = dentistClient.getById(request.dentistId());

        AppointmentTypeEntity type = appointmentTypeRepository.findById(request.appointmentTypeId())
                .orElseThrow(() -> new AppointmentTypeNotFoundException(request.appointmentTypeId()));

        if (repository.existsByDentistIdAndAppointmentDateAndStartTimeAndStatusNot(
                request.dentistId().intValue(), request.appointmentDate(), request.startTime(),
                AppointmentEntity.AppointmentStatus.CANCELADA)) {
            throw new InvalidAppointmentException(
                    "El personal clinico ya tiene una cita programada en esa fecha y hora.");
        }

        AppointmentEntity entity = mapper.toEntity(request);
        entity.setEndTime(request.startTime().plusMinutes(type.getDurationMinutes()));
        entity.setStatus(AppointmentEntity.AppointmentStatus.PENDIENTE);

        AppointmentEntity saved = repository.save(entity);

        eventPublisher.publishAppointmentCreated(new AppointmentCreatedEvent(
                saved.getId(), request.patientId(), request.dentistId(), saved.getAppointmentDate()
        ));

        return toResponse(saved, patient, dentist, type);
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentResponseDto getById(Long id) {
        AppointmentEntity entity = repository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));
        return enrich(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponseDto> getByPatient(Long patientId) {
        return repository.findByPatientId(patientId.intValue())
                .stream()
                .map(this::enrich)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponseDto> getByDentist(Long dentistId) {
        return repository.findByDentistId(dentistId.intValue())
                .stream()
                .map(this::enrich)
                .toList();
    }

    @Override
    @Transactional
    public AppointmentResponseDto reschedule(Long id, RescheduleRequestDto request) {
        AppointmentEntity entity = repository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));

        if (entity.getStatus() == AppointmentEntity.AppointmentStatus.CANCELADA
                || entity.getStatus() == AppointmentEntity.AppointmentStatus.COMPLETADA) {
            throw new InvalidAppointmentException("No se puede reprogramar una cita cancelada o completada.");
        }

        AppointmentTypeEntity type = appointmentTypeRepository.findById(entity.getAppointmentTypeId())
                .orElseThrow(() -> new AppointmentTypeNotFoundException(entity.getAppointmentTypeId()));

        if (repository.existsByDentistIdAndAppointmentDateAndStartTimeAndStatusNot(
                entity.getDentistId(), request.appointmentDate(), request.startTime(),
                AppointmentEntity.AppointmentStatus.CANCELADA)) {
            throw new InvalidAppointmentException(
                    "El personal clinico ya tiene una cita programada en esa fecha y hora.");
        }

        entity.setAppointmentDate(request.appointmentDate());
        entity.setStartTime(request.startTime());
        entity.setEndTime(request.startTime().plusMinutes(type.getDurationMinutes()));
        entity.setStatus(AppointmentEntity.AppointmentStatus.REPROGRAMADA);

        return enrich(repository.save(entity));
    }

    @Override
    @Transactional
    public AppointmentResponseDto cancel(Long id) {
        AppointmentEntity entity = repository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));
        entity.setStatus(AppointmentEntity.AppointmentStatus.CANCELADA);
        return enrich(repository.save(entity));
    }

    private AppointmentResponseDto enrich(AppointmentEntity entity) {
        PatientDto patient = patientClient.getById(entity.getPatientId().longValue());
        DentistDto dentist = dentistClient.getById(entity.getDentistId().longValue());
        AppointmentTypeEntity type = appointmentTypeRepository.findById(entity.getAppointmentTypeId())
                .orElse(null);
        return toResponse(entity, patient, dentist, type);
    }

    private AppointmentResponseDto toResponse(AppointmentEntity entity, PatientDto patient, DentistDto dentist, AppointmentTypeEntity type) {
        AppointmentResponseDto base = mapper.toResponseDto(entity);
        return new AppointmentResponseDto(
                base.id(),
                base.patientId(),
                patient != null ? patient.firstName() + " " + patient.lastName() : null,
                base.dentistId(),
                dentist != null ? dentist.firstName() + " " + dentist.lastName() : null,
                base.appointmentTypeId(),
                type != null ? type.getName() : null,
                base.appointmentDate(),
                base.startTime(),
                base.endTime(),
                base.reason(),
                base.status(),
                base.amount()
        );
    }
}
