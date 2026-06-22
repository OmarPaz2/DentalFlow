package com.dentalflow.payment_service.domain.services;

import com.dentalflow.payment_service.api.dtos.PaymentRequestDto;
import com.dentalflow.payment_service.api.dtos.PaymentResponseDto;

import java.math.BigDecimal;

public interface IPaymentService {

    PaymentResponseDto registerPagoTratamiento(PaymentRequestDto pago, Integer idTratamiento);

    PaymentResponseDto registerPagoCita(PaymentRequestDto pago, Long idCita);

    PaymentResponseDto findPagoById(Integer idPago);

    BigDecimal sumMontoByTreatmentId(Integer idTratamiento);
}
