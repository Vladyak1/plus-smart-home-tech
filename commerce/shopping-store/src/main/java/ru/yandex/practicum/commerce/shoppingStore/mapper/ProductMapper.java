package ru.yandex.practicum.commerce.shoppingStore.mapper;

import ru.yandex.practicum.commerce.api.dto.ProductDto;
import ru.yandex.practicum.commerce.shoppingStore.model.Product;


public interface ProductMapper {

    ProductDto toProductDto(Product product);

    Product toProduct(ProductDto productDto);

}
