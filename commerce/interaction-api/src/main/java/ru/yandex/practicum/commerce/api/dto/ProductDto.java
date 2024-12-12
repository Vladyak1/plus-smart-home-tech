package ru.yandex.practicum.commerce.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import ru.yandex.practicum.commerce.api.dto.enums.ProductCategory;
import ru.yandex.practicum.commerce.api.dto.enums.ProductState;
import ru.yandex.practicum.commerce.api.dto.enums.QuantityState;

import java.util.UUID;

@Builder
public record ProductDto (

        @NotBlank
        UUID productId,

        @NotBlank
        String productName,

        @NotBlank
        String description,

        @NotBlank
        String imageSrc,

        @NotBlank
        QuantityState quantityState,

        @NotBlank
        ProductState productState,

        double rating,

        @NotBlank
        ProductCategory productCategory,

        @Min(1)
        float price

) {
}
