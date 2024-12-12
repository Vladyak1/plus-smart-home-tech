package ru.yandex.practicum.commerce.api.dto.exception;

public class ProductInShoppingCartNotInWarehouse extends RuntimeException {
    ProductInShoppingCartNotInWarehouse(String message) {
        super(message);
    }

}
