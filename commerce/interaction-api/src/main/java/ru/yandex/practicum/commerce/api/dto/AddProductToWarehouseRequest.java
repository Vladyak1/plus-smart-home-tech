package ru.yandex.practicum.commerce.api.dto;

import jakarta.validation.constraints.NotBlank;

public record AddProductToWarehouseRequest(

        @NotBlank
        String productId,

        long quantity

) {
}
