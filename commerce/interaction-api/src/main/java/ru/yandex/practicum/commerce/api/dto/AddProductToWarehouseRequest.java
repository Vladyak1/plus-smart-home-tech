package ru.yandex.practicum.commerce.api.dto;

import jakarta.validation.constraints.NotBlank;

// Запрос на увеличение единиц товара по его идентификатору
public record AddProductToWarehouseRequest(

        @NotBlank
        String productId,

        long quantity

) {
}
