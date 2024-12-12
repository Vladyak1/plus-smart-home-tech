package ru.yandex.practicum.commerce.api.dto.exception;

public class NoSpecifiedProductInWareHouseException extends RuntimeException {
    public NoSpecifiedProductInWareHouseException(String message) {
        super(message);
    }
}
