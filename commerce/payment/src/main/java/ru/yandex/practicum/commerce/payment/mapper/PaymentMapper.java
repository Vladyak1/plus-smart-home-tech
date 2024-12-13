package ru.yandex.practicum.commerce.payment.mapper;

import ru.yandex.practicum.commerce.api.dto.PaymentDto;
import ru.yandex.practicum.commerce.payment.model.Payment;

public interface PaymentMapper {

    PaymentDto toPaymentDto(Payment payment);

}
