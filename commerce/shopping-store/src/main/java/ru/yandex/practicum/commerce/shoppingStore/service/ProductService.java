package ru.yandex.practicum.commerce.shoppingStore.service;

import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.commerce.api.dto.ProductDto;
import ru.yandex.practicum.commerce.api.dto.SetProductQuantityStateRequest;
import ru.yandex.practicum.commerce.api.dto.enums.ProductCategory;

import java.util.List;

public interface ProductService {

    List<ProductDto> getAll(ProductCategory category, Pageable pageable);

    ProductDto create(ProductDto productDto);

    ProductDto get(String productId);

    ProductDto update(ProductDto productDto);

    boolean isDeleted(String productId);

    boolean changeState(SetProductQuantityStateRequest stateRequest);

}
