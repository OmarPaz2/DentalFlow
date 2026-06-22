package com.dentalflow.appointment_service.client;

import com.dentalflow.appointment_service.client.dtos.PatientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patient-service", fallbackFactory = PatientClientFallbackFactory.class)
public interface PatientClient {

    @GetMapping("/api/v1/patients/{id}")
    PatientDto getById(@PathVariable("id") Long id);
}
