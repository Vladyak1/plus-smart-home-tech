package ru.yandex.practicum.commerce.api.dto;

import jakarta.validation.constraints.NotBlank;
import ru.yandex.practicum.commerce.api.dto.enums.QuantityState;

public record SetProductQuantityStateRequest(

        @NotBlank
        String productId,

        @NotBlank
        QuantityState quantityState
) {

}
