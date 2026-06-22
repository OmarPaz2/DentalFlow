package com.dentalflow.payment_service.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * payment-service publica dos tipos de eventos:
 *  - "payment.completed": generico, lo consume dashboard-service para su contador de pagos.
 *  - "payment.appointment.paid": solo cuando el pago corresponde a una cita,
 *    lo consume appointment-service para auto-confirmar la cita.
 */
@Configuration
public class RabbitMQConfig {

    public static final String ROUTING_KEY_PAYMENT_COMPLETED = "payment.completed";
    public static final String ROUTING_KEY_PAYMENT_APPOINTMENT_PAID = "payment.appointment.paid";

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
