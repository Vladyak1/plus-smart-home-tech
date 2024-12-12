package ru.yandex.practicum.commerce.shoppingCart.service;

import ru.yandex.practicum.commerce.api.dto.BookedProductsDto;
import ru.yandex.practicum.commerce.api.dto.ChangeProductQuantityRequest;
import ru.yandex.practicum.commerce.api.dto.ShoppingCartDto;

import java.util.Map;
import java.util.UUID;

public interface CartService {

    ShoppingCartDto get(String username);

    ShoppingCartDto addProducts(String username, Map<UUID, Long> products);

    void deactivate(String username);

    ShoppingCartDto update(String username, Map<UUID, Long> products);

    ShoppingCartDto changeProductQuantity(
            String username, ChangeProductQuantityRequest changeProductQuantityRequest);

    BookedProductsDto checkForProductsSufficiency(String username);

    ShoppingCartDto getById(UUID cartId);



}
