package com.dentalflow.payment_service.client;

import com.dentalflow.payment_service.client.dtos.AppointmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "appointment-service", fallbackFactory = AppointmentClientFallbackFactory.class)
public interface AppointmentClient {

    @GetMapping("/api/v1/appointments/{id}")
    AppointmentDto getById(@PathVariable("id") Long id);
}
