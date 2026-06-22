package com.dentalflow.dashboard_service.messaging;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TreatmentCompletedEvent(
        Integer treatmentId,
        Integer patientId
) {
}
