package com.dentalflow.appointment_service.client;

import com.dentalflow.appointment_service.api.exceptions.DownstreamServiceUnavailableException;
import com.dentalflow.appointment_service.api.exceptions.PatientReferenceNotFoundException;
import com.dentalflow.appointment_service.client.dtos.PatientDto;
import feign.FeignException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class PatientClientFallbackFactory implements FallbackFactory<PatientClient> {

    @Override
    public PatientClient create(Throwable cause) {
        return id -> {
            if (cause instanceof FeignException.NotFound) {
                throw new PatientReferenceNotFoundException(id);
            }
            throw new DownstreamServiceUnavailableException("patient-service");
        };
    }
}
