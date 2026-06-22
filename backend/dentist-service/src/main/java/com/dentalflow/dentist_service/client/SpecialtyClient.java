package com.dentalflow.dentist_service.client;

import com.dentalflow.dentist_service.client.dtos.SpecialtyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Synchronous call to specialty-service. The "name" is resolved through
 * Eureka, so no hardcoded host/port is needed - the load balancer picks an
 * available instance. Resilience4j wraps every call (see
 * resilience4j.circuitbreaker.instances.specialtyService in application.yaml)
 * and falls back to {@link SpecialtyClientFallback} when specialty-service is
 * slow or unreachable.
 */
@FeignClient(name = "specialty-service", fallback = SpecialtyClientFallback.class)
public interface SpecialtyClient {

    @GetMapping("/api/v1/specialties/{id}")
    SpecialtyDto getById(@PathVariable("id") Long id);
}
