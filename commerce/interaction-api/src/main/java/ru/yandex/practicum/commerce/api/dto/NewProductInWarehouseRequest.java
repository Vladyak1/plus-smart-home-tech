package ru.yandex.practicum.commerce.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewProductInWarehouseRequest(

        @NotBlank
        String productId,

        boolean fragile,

        @NotNull
        DimensionDto dimension,

        double weight

) {
}
