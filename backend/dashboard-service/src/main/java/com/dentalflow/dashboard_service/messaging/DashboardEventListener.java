package com.dentalflow.dashboard_service.messaging;

import com.dentalflow.dashboard_service.config.RabbitMQConfig;
import com.dentalflow.dashboard_service.domain.services.IDashboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DashboardEventListener {

    private static final Logger log = LoggerFactory.getLogger(DashboardEventListener.class);

    private final IDashboardService dashboardService;

    public DashboardEventListener(IDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_APPOINTMENT_CREATED)
    public void onAppointmentCreated(AppointmentCreatedEvent event) {
        log.info("Cita creada: {} - actualizando contador citasHoy", event.appointmentId());
        dashboardService.incrementCitasHoy();
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_TREATMENT_COMPLETED)
    public void onTreatmentCompleted(TreatmentCompletedEvent event) {
        log.info("Tratamiento completado: {} - actualizando contador tratamientosCompletados", event.treatmentId());
        dashboardService.incrementTratamientosCompletados();
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_PAYMENT_COMPLETED)
    public void onPaymentCompleted(PaymentCompletedEvent event) {
        log.info("Pago registrado: {} ({}) por {}", event.paymentId(), event.razon(), event.amount());
        dashboardService.registrarPago(event.amount());
    }
}
