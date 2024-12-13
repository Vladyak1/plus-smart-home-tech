package ru.yandex.practicum.commerce.shoppingStore.service;

import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.commerce.api.dto.ProductDto;
import ru.yandex.practicum.commerce.api.dto.SetProductQuantityStateRequest;
import ru.yandex.practicum.commerce.api.dto.enums.ProductCategory;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<ProductDto> getAll(ProductCategory category, Pageable pageable);

    ProductDto create(ProductDto productDto);

    ProductDto get(UUID productId);

    ProductDto update(ProductDto productDto);

    boolean isDeleted(UUID productId);

    boolean changeState(SetProductQuantityStateRequest stateRequest);

    List<ProductDto> getProductsByIds(List<UUID> ids);
}
