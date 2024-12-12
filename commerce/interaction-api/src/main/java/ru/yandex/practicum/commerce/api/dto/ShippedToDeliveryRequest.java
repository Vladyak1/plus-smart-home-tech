package ru.yandex.practicum.commerce.api.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ShippedToDeliveryRequest(
        UUID orderId,
        UUID deliveryId
) {
}
