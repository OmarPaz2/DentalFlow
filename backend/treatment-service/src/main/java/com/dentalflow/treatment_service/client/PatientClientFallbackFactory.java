package com.dentalflow.treatment_service.client;

import com.dentalflow.treatment_service.api.exceptions.DownstreamServiceUnavailableException;
import com.dentalflow.treatment_service.api.exceptions.PatientReferenceNotFoundException;
import com.dentalflow.treatment_service.client.dtos.PatientDto;
import com.dentalflow.treatment_service.client.dtos.PatientSearchResponseDto;
import feign.FeignException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PatientClientFallbackFactory implements FallbackFactory<PatientClient> {

    @Override
    public PatientClient create(Throwable cause) {
        return new PatientClient() {
            @Override
            public PatientDto getById(Long id) {
                if (cause instanceof FeignException.NotFound) {
                    throw new PatientReferenceNotFoundException("id " + id);
                }
                throw new DownstreamServiceUnavailableException("patient-service");
            }

            @Override
            public PatientSearchResponseDto searchByDni(String dni) {
                if (cause instanceof FeignException.NotFound) {
                    return new PatientSearchResponseDto("No encontrado", List.of());
                }
                throw new DownstreamServiceUnavailableException("patient-service");
            }
        };
    }
}
