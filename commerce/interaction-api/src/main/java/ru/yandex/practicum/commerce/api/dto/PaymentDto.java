package ru.yandex.practicum.commerce.api.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record PaymentDto(
        UUID paymentId,
        Float totalPayment,
        Float deliveryTotal,
        Float feeTotal
) {
}
