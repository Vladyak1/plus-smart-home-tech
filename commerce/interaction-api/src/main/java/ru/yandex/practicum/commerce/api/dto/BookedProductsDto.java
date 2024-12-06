package ru.yandex.practicum.commerce.api.dto;

import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record BookedProductsDto(

        @Positive
        double deliveryWeight,

        @Positive
        double deliveryVolume,

        boolean fragile
) {
}
