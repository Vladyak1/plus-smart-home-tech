package ru.yandex.practicum.commerce.api.dto;

import lombok.Builder;

import java.util.Map;

@Builder
public record ShoppingCartDto(

        String shoppingCartId, // Идентификатор корзины в БД

        Map<String, Long> products // Отображение идентификатора товара на отобранное количество.

) {
}
