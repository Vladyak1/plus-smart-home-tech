package ru.yandex.practicum.commerce.api.dto.exception;

public class NoPaymentFoundException extends RuntimeException {
    public NoPaymentFoundException(String message) {
        super(message);
    }
}
