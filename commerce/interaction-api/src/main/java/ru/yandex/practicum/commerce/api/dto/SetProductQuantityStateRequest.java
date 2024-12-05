package ru.yandex.practicum.commerce.api.dto;

import jakarta.validation.constraints.NotBlank;
import ru.yandex.practicum.commerce.api.dto.enums.QuantityState;

public record SetProductQuantityStateRequest(

        @NotBlank
        String productId, // Идентификатор товара

        @NotBlank
        QuantityState quantityState // Статус, перечисляющий состояние остатка как свойства товара
) {

}
