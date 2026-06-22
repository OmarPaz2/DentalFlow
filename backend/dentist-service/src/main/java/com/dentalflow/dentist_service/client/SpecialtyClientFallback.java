package com.dentalflow.dentist_service.client;

import com.dentalflow.dentist_service.client.dtos.SpecialtyDto;
import org.springframework.stereotype.Component;

/**
 * Fallback used by the circuit breaker when specialty-service is
 * unreachable/slow. We never let a downed specialty-service block staff
 * management: we simply return a placeholder so the caller can still see the
 * staff record, just without the human-readable specialty name.
 */
@Component
public class SpecialtyClientFallback implements SpecialtyClient {

    @Override
    public SpecialtyDto getById(Long id) {
        return new SpecialtyDto(id, "No disponible");
    }
}
