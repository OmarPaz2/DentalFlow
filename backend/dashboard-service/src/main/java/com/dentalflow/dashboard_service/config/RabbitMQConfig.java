package com.dentalflow.dashboard_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * dashboard-service no publica nada: solo escucha los eventos de negocio de
 * otros servicios para mantener sus contadores actualizados sin tener que
 * consultar a cada servicio en cada request.
 */
@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_APPOINTMENT_CREATED = "dashboard-service.appointment.created.queue";
    public static final String QUEUE_TREATMENT_COMPLETED = "dashboard-service.treatment.completed.queue";
    public static final String QUEUE_PAYMENT_COMPLETED = "dashboard-service.payment.completed.queue";

    @Value("${dentalflow.rabbitmq.exchange}")
    private String exchangeName;

    @Bean
    public TopicExchange dentalflowEventsExchange() {
        return new TopicExchange(exchangeName, true, false);
    }

    @Bean
    public Queue appointmentCreatedQueue() {
        return QueueBuilder.durable(QUEUE_APPOINTMENT_CREATED).build();
    }

    @Bean
    public Queue treatmentCompletedQueue() {
        return QueueBuilder.durable(QUEUE_TREATMENT_COMPLETED).build();
    }

    @Bean
    public Queue paymentCompletedQueue() {
        return QueueBuilder.durable(QUEUE_PAYMENT_COMPLETED).build();
    }

    @Bean
    public Binding appointmentCreatedBinding(Queue appointmentCreatedQueue, TopicExchange dentalflowEventsExchange) {
        return BindingBuilder.bind(appointmentCreatedQueue).to(dentalflowEventsExchange).with("appointment.created");
    }

    @Bean
    public Binding treatmentCompletedBinding(Queue treatmentCompletedQueue, TopicExchange dentalflowEventsExchange) {
        return BindingBuilder.bind(treatmentCompletedQueue).to(dentalflowEventsExchange).with("treatment.completed");
    }

    @Bean
    public Binding paymentCompletedBinding(Queue paymentCompletedQueue, TopicExchange dentalflowEventsExchange) {
        return BindingBuilder.bind(paymentCompletedQueue).to(dentalflowEventsExchange).with("payment.completed");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
