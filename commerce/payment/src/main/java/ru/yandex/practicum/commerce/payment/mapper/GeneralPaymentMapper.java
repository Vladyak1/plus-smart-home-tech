package ru.yandex.practicum.commerce.payment.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.commerce.api.dto.PaymentDto;
import ru.yandex.practicum.commerce.payment.model.Payment;

@Component
public class GeneralPaymentMapper implements PaymentMapper {

    @Override
    public PaymentDto toPaymentDto(Payment payment) {
        return PaymentDto.builder()
                .totalPayment(payment.getTotalPayment())
                .deliveryTotal(payment.getDeliveryTotal())
                .feeTotal(payment.getFeeTotal())
                .build();
    }
}
