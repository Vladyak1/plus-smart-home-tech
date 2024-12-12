package ru.yandex.practicum.commerce.api.dto.exception;

public class NoOrderFoundException extends RuntimeException {
    public NoOrderFoundException(String message) {
        super(message);
    }
}
