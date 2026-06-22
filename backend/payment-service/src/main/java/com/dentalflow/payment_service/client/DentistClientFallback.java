package com.dentalflow.payment_service.client;

import com.dentalflow.payment_service.client.dtos.DentistDto;
import org.springframework.stereotype.Component;

@Component
public class DentistClientFallback implements DentistClient {

    @Override
    public DentistDto getById(Long id) {
        return new DentistDto(id, "No disponible", "No disponible", "No disponible");
    }
}
