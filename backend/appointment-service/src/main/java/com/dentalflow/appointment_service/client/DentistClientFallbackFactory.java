package com.dentalflow.appointment_service.client;

import com.dentalflow.appointment_service.api.exceptions.DentistReferenceNotFoundException;
import com.dentalflow.appointment_service.api.exceptions.DownstreamServiceUnavailableException;
import com.dentalflow.appointment_service.client.dtos.DentistDto;
import feign.FeignException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class DentistClientFallbackFactory implements FallbackFactory<DentistClient> {

    @Override
    public DentistClient create(Throwable cause) {
        return id -> {
            if (cause instanceof FeignException.NotFound) {
                throw new DentistReferenceNotFoundException(id);
            }
            throw new DownstreamServiceUnavailableException("dentist-service");
        };
    }
}
