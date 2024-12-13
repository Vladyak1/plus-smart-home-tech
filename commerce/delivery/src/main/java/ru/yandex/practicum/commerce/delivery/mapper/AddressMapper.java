package ru.yandex.practicum.commerce.delivery.mapper;

import ru.yandex.practicum.commerce.api.dto.AddressDto;
import ru.yandex.practicum.commerce.delivery.model.Address;

public interface AddressMapper {

    Address toAddress(AddressDto addressDto);

    AddressDto toAddressDto(Address address);
}
