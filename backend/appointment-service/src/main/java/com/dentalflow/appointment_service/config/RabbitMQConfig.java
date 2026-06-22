package com.dentalflow.appointment_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Messaging topology for appointment-service.
 * <p>
 * Publishes:
 *  - routing key "appointment.created"  -> consumed by dashboard-service to bump its "citasDelDia" counter.
 * <p>
 * Consumes:
 *  - routing key "payment.appointment.paid" (published by payment-service) -> auto-confirms the appointment,
 *    decoupling payment-service from having to know anything about appointment status transitions.
 */
@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_PAYMENT_APPOINTMENT_PAID = "appointment-service.payment.appointment.paid.queue";
    public static final String ROUTING_KEY_APPOINTMENT_CREATED = "appointment.created";
    public static final String ROUTING_KEY_PAYMENT_APPOINTMENT_PAID = "payment.appointment.paid";

    @Value("${dentalflow.rabbitmq.exchange}")
    private String exchangeName;

    @Bean
    public TopicExchange dentalflowEventsExchange() {
        return new TopicExchange(exchangeName, true, false);
    }

    @Bean
    public Queue paymentAppointmentPaidQueue() {
        return QueueBuilder.durable(QUEUE_PAYMENT_APPOINTMENT_PAID).build();
    }

    @Bean
    public Binding paymentAppointmentPaidBinding(Queue paymentAppointmentPaidQueue, TopicExchange dentalflowEventsExchange) {
        return BindingBuilder.bind(paymentAppointmentPaidQueue)
                .to(dentalflowEventsExchange)
                .with(ROUTING_KEY_PAYMENT_APPOINTMENT_PAID);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
