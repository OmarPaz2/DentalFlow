package com.dentalflow.payment_service.client;

import com.dentalflow.payment_service.api.exceptions.DownstreamServiceUnavailableException;
import com.dentalflow.payment_service.api.exceptions.TreatmentReferenceNotFoundException;
import com.dentalflow.payment_service.client.dtos.ActiveSessionDto;
import com.dentalflow.payment_service.client.dtos.TreatmentCostSummaryDto;
import com.dentalflow.payment_service.client.dtos.TreatmentDto;
import feign.FeignException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class TreatmentClientFallbackFactory implements FallbackFactory<TreatmentClient> {

    @Override
    public TreatmentClient create(Throwable cause) {
        return new TreatmentClient() {
            @Override
            public TreatmentDto getTreatment(Integer id) {
                if (cause instanceof FeignException.NotFound) {
                    throw new TreatmentReferenceNotFoundException(id);
                }
                throw new DownstreamServiceUnavailableException("treatment-service");
            }

            @Override
            public TreatmentCostSummaryDto getCostSummary(Integer id) {
                if (cause instanceof FeignException.NotFound) {
                    throw new TreatmentReferenceNotFoundException(id);
                }
                throw new DownstreamServiceUnavailableException("treatment-service");
            }

            @Override
            public ResponseEntity<ActiveSessionDto> getActiveSession(Integer id) {
                throw new DownstreamServiceUnavailableException("treatment-service");
            }
        };
    }
}
