package com.dentalflow.payment_service.client;

import com.dentalflow.payment_service.client.dtos.ActiveSessionDto;
import com.dentalflow.payment_service.client.dtos.TreatmentCostSummaryDto;
import com.dentalflow.payment_service.client.dtos.TreatmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "treatment-service", fallbackFactory = TreatmentClientFallbackFactory.class)
public interface TreatmentClient {

    @GetMapping("/api/v1/treatments/{id}")
    TreatmentDto getTreatment(@PathVariable("id") Integer id);

    @GetMapping("/api/v1/treatments/{id}/cost-summary")
    TreatmentCostSummaryDto getCostSummary(@PathVariable("id") Integer id);

    @GetMapping("/api/v1/treatments/{id}/active-session")
    ResponseEntity<ActiveSessionDto> getActiveSession(@PathVariable("id") Integer id);
}
