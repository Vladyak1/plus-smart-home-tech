package ru.yandex.practicum.commerce.delivery.service;

import ru.yandex.practicum.commerce.api.dto.DeliveryDto;
import ru.yandex.practicum.commerce.api.dto.OrderDto;

import java.util.UUID;

public interface DeliveryService {

    DeliveryDto create(DeliveryDto deliveryDto);

    void setSuccessfulToDelivery(UUID orderId);

    void setPickedToDelivery(UUID orderId);

    void setFailedToDelivery(UUID orderId);

    float calculateDeliveryCost(OrderDto orderDto);

}
