package ru.yandex.practicum.commerce.api.dto.exception;

public class NotAuthorizedUserException extends RuntimeException {

    public NotAuthorizedUserException(String message) {
        super(message);
    }
}
