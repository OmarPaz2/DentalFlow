package com.dentalflow.payment_service.client;

import com.dentalflow.payment_service.api.exceptions.AppointmentReferenceNotFoundException;
import com.dentalflow.payment_service.api.exceptions.DownstreamServiceUnavailableException;
import com.dentalflow.payment_service.client.dtos.AppointmentDto;
import feign.FeignException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class AppointmentClientFallbackFactory implements FallbackFactory<AppointmentClient> {

    @Override
    public AppointmentClient create(Throwable cause) {
        return id -> {
            if (cause instanceof FeignException.NotFound) {
                throw new AppointmentReferenceNotFoundException(id);
            }
            throw new DownstreamServiceUnavailableException("appointment-service");
        };
    }
}
