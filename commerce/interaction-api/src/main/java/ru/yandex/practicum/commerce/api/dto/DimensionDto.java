package ru.yandex.practicum.commerce.api.dto;

import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record DimensionDto(

        @Min(1)
        double width,

        @Min(1)
        double height,

        @Min(1)
        double depth
) {
}
