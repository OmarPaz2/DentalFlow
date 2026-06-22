package com.dentalflow.treatment_service.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * treatment-service solo publica eventos (no consume nada). Routing key
 * "treatment.completed" se emite cuando se realiza la ultima sesion del
 * tratamiento, y la consume dashboard-service para mantener el contador de
 * tratamientos activos.
 */
@Configuration
public class RabbitMQConfig {

    public static final String ROUTING_KEY_TREATMENT_COMPLETED = "treatment.completed";

    @Value("${dentalflow.rabbitmq.exchange}")
    private String exchangeName;

    @Bean
    public TopicExchange dentalflowEventsExchange() {
        return new TopicExchange(exchangeName, true, false);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
