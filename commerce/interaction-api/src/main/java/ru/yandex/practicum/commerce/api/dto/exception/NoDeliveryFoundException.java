package ru.yandex.practicum.commerce.api.dto.exception;

public class NoDeliveryFoundException extends RuntimeException {
    public NoDeliveryFoundException(String message) {
        super(message);
    }
}
