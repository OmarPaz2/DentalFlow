package com.dentalflow.payment_service.domain.mappers;

import com.dentalflow.payment_service.api.dtos.PaymentRequestDto;
import com.dentalflow.payment_service.data.entities.PaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "treatmentId", ignore = true)
    @Mapping(target = "appointmentId", ignore = true)
    @Mapping(target = "fecha", ignore = true)
    @Mapping(target = "metodoPago", source = "metodoPago", qualifiedByName = "stringToMetodo")
    PaymentEntity toEntity(PaymentRequestDto dto);

    @Named("stringToMetodo")
    default PaymentEntity.MetodoPago stringToMetodo(String metodo) {
        return metodo == null ? null : PaymentEntity.MetodoPago.valueOf(metodo);
    }
}
