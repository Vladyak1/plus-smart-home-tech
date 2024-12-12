package ru.yandex.practicum.commerce.api.dto;

import lombok.Builder;
import ru.yandex.practicum.commerce.api.dto.enums.DeliveryState;

import java.util.UUID;

@Builder
public record DeliveryDto(
        AddressDto fromAddress,
        AddressDto toAddress,
        UUID orderId,
        DeliveryState deliveryState
) {
}
