package ru.yandex.practicum.commerce.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import ru.yandex.practicum.commerce.api.dto.enums.ProductCategory;
import ru.yandex.practicum.commerce.api.dto.enums.ProductState;
import ru.yandex.practicum.commerce.api.dto.enums.QuantityState;

@Builder
public record ProductDto (

        @NotBlank
        String productId, // Идентификатор товара в БД

        @NotBlank
        String productName, // Наименование товара

        @NotBlank
        String description, // Описание товара

        @NotBlank
        String imageSrc, // Ссылка на картинку во внешнем хранилище или SVG

        @NotBlank
        QuantityState quantityState, // Статус, перечисляющий состояние остатка как свойства товара

        @NotBlank
        ProductState productState, // Статус товара

        double rating, // Рейтинг товара на основе оценок пользователей

        @NotBlank
        ProductCategory productCategory, // Категория товара

        @Min(1)
        float price // Цена товара

) {
}
