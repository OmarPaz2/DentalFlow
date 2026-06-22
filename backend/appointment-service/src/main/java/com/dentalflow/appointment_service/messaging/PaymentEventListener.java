package com.dentalflow.appointment_service.messaging;

import com.dentalflow.appointment_service.config.RabbitMQConfig;
import com.dentalflow.appointment_service.data.entities.AppointmentEntity;
import com.dentalflow.appointment_service.data.repositories.IAppointmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PaymentEventListener {

    private static final Logger log = LoggerFactory.getLogger(PaymentEventListener.class);

    private final IAppointmentRepository appointmentRepository;

    public PaymentEventListener(IAppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_PAYMENT_APPOINTMENT_PAID)
    @Transactional
    public void onPaymentAppointmentPaid(PaymentAppointmentPaidEvent event) {
        appointmentRepository.findById(event.appointmentId()).ifPresentOrElse(appointment -> {
            if (appointment.getStatus() == AppointmentEntity.AppointmentStatus.PENDIENTE
                    || appointment.getStatus() == AppointmentEntity.AppointmentStatus.REPROGRAMADA) {
                appointment.setStatus(AppointmentEntity.AppointmentStatus.CONFIRMADA);
                appointmentRepository.save(appointment);
                log.info("Cita {} confirmada automaticamente tras pago {}", appointment.getId(), event.paymentId());
            }
        }, () -> log.warn("Se recibio evento de pago para una cita inexistente: {}", event.appointmentId()));
    }
}
