package ru.yandex.practicum.commerce.api.dto.enums;

public enum OrderState {
    NEW,
    ON_PAYMENT,
    ON_DELIVERY,
    DONE,
    DELIVERED,
    ASSEMBLED,
    PAID,
    COMPLETED,
    DELIVERY_FAILED,
    ASSEMBLED_FAILED,
    PAYMENT_FAILED,
    PRODUCT_RETURNED,
    CANCELED
}
