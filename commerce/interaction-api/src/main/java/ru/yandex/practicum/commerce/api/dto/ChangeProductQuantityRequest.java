package ru.yandex.practicum.commerce.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

public record ChangeProductQuantityRequest(

        @NotBlank
        UUID productId,

        @PositiveOrZero
        long newQuantity
) {
}
