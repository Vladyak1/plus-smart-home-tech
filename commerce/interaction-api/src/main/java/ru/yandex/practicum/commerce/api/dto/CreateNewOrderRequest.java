package ru.yandex.practicum.commerce.api.dto;

public record CreateNewOrderRequest(
        ShoppingCartDto shoppingCart,
        AddressDto deliveryAddress
) {
}
