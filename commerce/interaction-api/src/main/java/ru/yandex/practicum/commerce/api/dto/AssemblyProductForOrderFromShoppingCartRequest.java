package ru.yandex.practicum.commerce.api.dto;

import jakarta.validation.constraints.NotBlank;

public record AssemblyProductForOrderFromShoppingCartRequest(

        @NotBlank
        String shoppingCartId,

        @NotBlank
        String orderId
) {
}
