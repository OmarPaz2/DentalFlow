package com.dentalflow.treatment_service.messaging;

public record TreatmentCompletedEvent(
        Integer treatmentId,
        Integer patientId
) {
}
