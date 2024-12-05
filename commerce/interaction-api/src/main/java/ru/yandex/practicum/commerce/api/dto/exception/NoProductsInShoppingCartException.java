package ru.yandex.practicum.commerce.api.dto.exception;

public class NoProductsInShoppingCartException extends RuntimeException{

    public NoProductsInShoppingCartException(String message) {
        super(message);
    }
}
