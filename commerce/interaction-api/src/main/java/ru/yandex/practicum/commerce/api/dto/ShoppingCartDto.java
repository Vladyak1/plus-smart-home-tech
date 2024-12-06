package ru.yandex.practicum.commerce.api.dto;

import lombok.Builder;

import java.util.Map;

@Builder
public record ShoppingCartDto(

        String shoppingCartId,

        Map<String, Long> products

) {
}
