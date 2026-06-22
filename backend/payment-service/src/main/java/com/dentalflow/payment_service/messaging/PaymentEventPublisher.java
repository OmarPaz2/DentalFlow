package com.dentalflow.payment_service.messaging;

import com.dentalflow.payment_service.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${dentalflow.rabbitmq.exchange}")
    private String exchangeName;

    public PaymentEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishPaymentCompleted(PaymentCompletedEvent event) {
        rabbitTemplate.convertAndSend(exchangeName, RabbitMQConfig.ROUTING_KEY_PAYMENT_COMPLETED, event);
    }

    public void publishPaymentAppointmentPaid(PaymentAppointmentPaidEvent event) {
        rabbitTemplate.convertAndSend(exchangeName, RabbitMQConfig.ROUTING_KEY_PAYMENT_APPOINTMENT_PAID, event);
    }
}
