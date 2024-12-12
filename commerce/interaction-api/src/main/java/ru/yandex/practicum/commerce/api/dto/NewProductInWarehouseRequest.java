package ru.yandex.practicum.commerce.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record NewProductInWarehouseRequest(

        @NotBlank
        UUID productId,

        boolean fragile,

        @NotNull
        DimensionDto dimension,

        double weight

) {
}
