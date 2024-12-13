package ru.yandex.practicum.commerce.api.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record AddProductToWarehouseRequest(

        @NotBlank
        UUID productId,

        long quantity

) {
}
