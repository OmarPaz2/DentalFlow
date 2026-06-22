package com.dentalflow.treatment_service.client;

import com.dentalflow.treatment_service.client.dtos.PatientDto;
import com.dentalflow.treatment_service.client.dtos.PatientSearchResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "patient-service", fallbackFactory = PatientClientFallbackFactory.class)
public interface PatientClient {

    @GetMapping("/api/v1/patients/{id}")
    PatientDto getById(@PathVariable("id") Long id);

    @GetMapping("/api/v1/patients")
    PatientSearchResponseDto searchByDni(@RequestParam("dni") String dni);
}
