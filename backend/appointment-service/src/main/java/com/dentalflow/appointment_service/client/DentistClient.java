package com.dentalflow.appointment_service.client;

import com.dentalflow.appointment_service.client.dtos.DentistDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "dentist-service", fallbackFactory = DentistClientFallbackFactory.class)
public interface DentistClient {

    @GetMapping("/api/v1/dentists/{id}")
    DentistDto getById(@PathVariable("id") Long id);
}
