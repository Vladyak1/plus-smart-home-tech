package ru.yandex.practicum.commerce.api.dto;

import jakarta.validation.constraints.NotBlank;

// Запрос на сбор заказа из ранее зарезервированных товаров из корзины к заказу.
public record AssemblyProductForOrderFromShoppingCartRequest(

        @NotBlank
        String shoppingCartId,

        @NotBlank
        String orderId
) {
}
