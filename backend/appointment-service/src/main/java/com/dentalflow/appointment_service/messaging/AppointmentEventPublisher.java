package com.dentalflow.appointment_service.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppointmentEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${dentalflow.rabbitmq.exchange}")
    private String exchangeName;

    public AppointmentEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishAppointmentCreated(AppointmentCreatedEvent event) {
        rabbitTemplate.convertAndSend(
                exchangeName,
                com.dentalflow.appointment_service.config.RabbitMQConfig.ROUTING_KEY_APPOINTMENT_CREATED,
                event
        );
    }
}
