package com.dentalflow.payment_service.data.repositories;

import com.dentalflow.payment_service.data.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface IPaymentRepository extends JpaRepository<PaymentEntity, Integer> {

    @Query("SELECT COALESCE(SUM(p.monto), 0) FROM PaymentEntity p WHERE p.treatmentId = :treatmentId")
    BigDecimal sumMontoByTreatmentId(Integer treatmentId);
}
