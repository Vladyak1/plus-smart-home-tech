package ru.yandex.practicum.commerce.order.mapper;

import ru.yandex.practicum.commerce.api.dto.AddressDto;
import ru.yandex.practicum.commerce.order.model.Address;

public interface AddressMapper {

    Address toAddress(AddressDto addressDto);

}
