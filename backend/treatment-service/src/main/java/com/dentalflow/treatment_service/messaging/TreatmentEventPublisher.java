package com.dentalflow.treatment_service.messaging;

import com.dentalflow.treatment_service.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TreatmentEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${dentalflow.rabbitmq.exchange}")
    private String exchangeName;

    public TreatmentEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishTreatmentCompleted(TreatmentCompletedEvent event) {
        rabbitTemplate.convertAndSend(exchangeName, RabbitMQConfig.ROUTING_KEY_TREATMENT_COMPLETED, event);
    }
}
