package ru.yandex.practicum.commerce.delivery.mapper;

import ru.yandex.practicum.commerce.api.dto.DeliveryDto;
import ru.yandex.practicum.commerce.delivery.model.Delivery;

public interface DeliveryMapper {

    Delivery toDelivery(DeliveryDto deliveryDto);
    
    DeliveryDto toDeliveryDto(Delivery delivery);
}
