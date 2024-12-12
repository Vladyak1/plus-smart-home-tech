package ru.yandex.practicum.commerce.api.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record AssemblyProductForOrderFromShoppingCartRequest(

        @NotBlank
        UUID shoppingCartId,

        @NotBlank
        UUID orderId
) {
}
