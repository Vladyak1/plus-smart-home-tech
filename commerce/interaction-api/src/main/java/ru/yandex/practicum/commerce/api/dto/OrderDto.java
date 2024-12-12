package ru.yandex.practicum.commerce.api.dto;

import lombok.Builder;
import ru.yandex.practicum.commerce.api.dto.enums.OrderState;

import java.util.Map;
import java.util.UUID;

@Builder
public record OrderDto(
        UUID orderId,
        UUID shoppingCartId,
        Map<UUID, Long> products,
        UUID deliveryId,
        OrderState state,
        Double deliveryVolume,
        Double deliveryWeight,
        Boolean fragile,
        Float totalPrice,
        Float deliveryPrice,
        Float productPrice
) {
}
