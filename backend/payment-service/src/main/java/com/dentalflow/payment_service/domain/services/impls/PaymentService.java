package com.dentalflow.payment_service.domain.services.impls;

import com.dentalflow.payment_service.api.dtos.PaymentRequestDto;
import com.dentalflow.payment_service.api.dtos.PaymentResponseDto;
import com.dentalflow.payment_service.api.exceptions.InvalidPaymentException;
import com.dentalflow.payment_service.api.exceptions.PaymentNotFoundException;
import com.dentalflow.payment_service.client.AppointmentClient;
import com.dentalflow.payment_service.client.DentistClient;
import com.dentalflow.payment_service.client.TreatmentClient;
import com.dentalflow.payment_service.client.dtos.ActiveSessionDto;
import com.dentalflow.payment_service.client.dtos.AppointmentDto;
import com.dentalflow.payment_service.client.dtos.DentistDto;
import com.dentalflow.payment_service.client.dtos.TreatmentDto;
import com.dentalflow.payment_service.data.entities.PaymentEntity;
import com.dentalflow.payment_service.data.repositories.IPaymentRepository;
import com.dentalflow.payment_service.domain.mappers.PaymentMapper;
import com.dentalflow.payment_service.domain.services.IPaymentService;
import com.dentalflow.payment_service.messaging.PaymentAppointmentPaidEvent;
import com.dentalflow.payment_service.messaging.PaymentCompletedEvent;
import com.dentalflow.payment_service.messaging.PaymentEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class PaymentService implements IPaymentService {

    private final IPaymentRepository repository;
    private final PaymentMapper mapper;
    private final TreatmentClient treatmentClient;
    private final AppointmentClient appointmentClient;
    private final DentistClient dentistClient;
    private final PaymentEventPublisher eventPublisher;

    public PaymentService(IPaymentRepository repository,
                           PaymentMapper mapper,
                           TreatmentClient treatmentClient,
                           AppointmentClient appointmentClient,
                           DentistClient dentistClient,
                           PaymentEventPublisher eventPublisher) {
        this.repository = repository;
        this.mapper = mapper;
        this.treatmentClient = treatmentClient;
        this.appointmentClient = appointmentClient;
        this.dentistClient = dentistClient;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public PaymentResponseDto registerPagoTratamiento(PaymentRequestDto pago, Integer idTratamiento) {
        // Llamadas sincronas via Feign, protegidas por CircuitBreaker (ver application.yaml).
        // A diferencia de otros clientes "blandos", aqui un fallo de treatment-service
        // aborta la operacion (DownstreamServiceUnavailableException -> 503) porque
        // validar el monto contra la fuente de verdad es obligatorio antes de cobrar.
        TreatmentDto tratamiento = treatmentClient.getTreatment(idTratamiento);

        if (tratamiento.costoEstimado().compareTo(pago.monto()) < 0) {
            throw new InvalidPaymentException("El monto a pagar no puede ser mayor que el costo del tratamiento");
        }

        ResponseEntity<ActiveSessionDto> sesionResponse = treatmentClient.getActiveSession(idTratamiento);
        ActiveSessionDto sesionProgramada = sesionResponse.getStatusCode().is2xxSuccessful() ? sesionResponse.getBody() : null;

        if (sesionProgramada == null) {
            throw new InvalidPaymentException(
                    "No hay una sesion programada para este tratamiento; no se puede registrar el pago.");
        }

        BigDecimal montoRestante = tratamiento.costoEstimado()
                .subtract(repository.sumMontoByTreatmentId(idTratamiento));

        boolean montoValido = pago.monto().compareTo(sesionProgramada.costoParcial()) == 0
                || pago.monto().compareTo(montoRestante) <= 0;

        if (!montoValido) {
            throw new InvalidPaymentException(
                    "El monto a pagar no puede ser menor al monto de la sesion programada: " + sesionProgramada.costoParcial());
        }

        PaymentEntity entity = mapper.toEntity(pago);
        entity.setTreatmentId(idTratamiento);
        entity = repository.save(entity);

        eventPublisher.publishPaymentCompleted(
                new PaymentCompletedEvent(entity.getId(), "TRATAMIENTO", idTratamiento, entity.getMonto()));

        DentistDto odontologo = dentistClient.getById(tratamiento.dentistId().longValue());

        return new PaymentResponseDto(
                entity.getId(),
                "TRATAMIENTO",
                tratamiento.patientFirstName(),
                tratamiento.patientLastName(),
                odontologo != null ? odontologo.specialtyName() : null,
                entity.getMonto(),
                entity.getFecha(),
                entity.getMetodoPago().name()
        );
    }

    @Override
    @Transactional
    public PaymentResponseDto registerPagoCita(PaymentRequestDto pago, Long idCita) {
        AppointmentDto cita = appointmentClient.getById(idCita);

        if (cita.amount().compareTo(pago.monto()) != 0) {
            throw new InvalidPaymentException(
                    "El monto a pagar no puede ser diferente al precio de la cita: " + cita.amount());
        }

        PaymentEntity entity = mapper.toEntity(pago);
        entity.setAppointmentId(idCita.intValue());
        entity = repository.save(entity);

        eventPublisher.publishPaymentCompleted(
                new PaymentCompletedEvent(entity.getId(), "CITA", idCita.intValue(), entity.getMonto()));
        eventPublisher.publishPaymentAppointmentPaid(
                new PaymentAppointmentPaidEvent(entity.getId(), idCita, entity.getMonto()));

        String[] names = splitFullName(cita.patientFullName());

        return new PaymentResponseDto(
                entity.getId(),
                "CITA",
                names[0],
                names[1],
                null,
                entity.getMonto(),
                entity.getFecha(),
                entity.getMetodoPago().name()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponseDto findPagoById(Integer idPago) {
        PaymentEntity entity = repository.findById(idPago)
                .orElseThrow(() -> new PaymentNotFoundException(idPago));

        if (entity.getAppointmentId() != null) {
            AppointmentDto cita = appointmentClient.getById(entity.getAppointmentId().longValue());
            String[] names = splitFullName(cita.patientFullName());
            return new PaymentResponseDto(entity.getId(), "CITA", names[0], names[1], null,
                    entity.getMonto(), entity.getFecha(), entity.getMetodoPago().name());
        }

        TreatmentDto tratamiento = treatmentClient.getTreatment(entity.getTreatmentId());
        DentistDto odontologo = dentistClient.getById(tratamiento.dentistId().longValue());
        return new PaymentResponseDto(entity.getId(), "TRATAMIENTO", tratamiento.patientFirstName(),
                tratamiento.patientLastName(), odontologo != null ? odontologo.specialtyName() : null,
                entity.getMonto(), entity.getFecha(), entity.getMetodoPago().name());
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal sumMontoByTreatmentId(Integer idTratamiento) {
        return repository.sumMontoByTreatmentId(idTratamiento);
    }

    private String[] splitFullName(String fullName) {
        if (fullName == null || fullName.isBlank()) {
            return new String[]{null, null};
        }
        int idx = fullName.indexOf(' ');
        return idx > 0
                ? new String[]{fullName.substring(0, idx), fullName.substring(idx + 1)}
                : new String[]{fullName, null};
    }
}
