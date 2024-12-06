package ru.yandex.practicum.commerce.shoppingCart.service;

import ru.yandex.practicum.commerce.api.dto.BookedProductsDto;
import ru.yandex.practicum.commerce.api.dto.ChangeProductQuantityRequest;
import ru.yandex.practicum.commerce.api.dto.ShoppingCartDto;

import java.util.Map;

public interface CartService {

    ShoppingCartDto get(String username);

    ShoppingCartDto addProducts(String username, Map<String, Long> products);

    void deactivate(String username);

    ShoppingCartDto update(String username, Map<String, Long> products);

    ShoppingCartDto changeProductQuantity(
            String username, ChangeProductQuantityRequest changeProductQuantityRequest);

    BookedProductsDto book(String username);

    ShoppingCartDto getById(String username);



}
