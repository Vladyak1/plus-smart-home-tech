package ru.yandex.practicum.commerce.warehouse.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.commerce.api.dto.DimensionDto;
import ru.yandex.practicum.commerce.warehouse.model.Dimension;

@Component
public class DimensionMapper {

    public DimensionDto toDto(Dimension dimension) {
        return DimensionDto.builder()
                .width(dimension.getWidth())
                .height(dimension.getHeight())
                .depth(dimension.getDepth())
                .build();
    }

    public Dimension fromDto(DimensionDto dimensionDto) {
        return Dimension.builder()
                .width(dimensionDto.width())
                .height(dimensionDto.height())
                .depth(dimensionDto.depth())
                .build();
    }
}
