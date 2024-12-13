package ru.yandex.practicum.commerce.delivery.mapper.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.commerce.api.dto.AddressDto;
import ru.yandex.practicum.commerce.delivery.mapper.AddressMapper;
import ru.yandex.practicum.commerce.delivery.model.Address;

@Component
public class GeneralAddressMapper implements AddressMapper {


    @Override
    public Address toAddress(AddressDto addressDto) {
        return Address.builder()
                .country(addressDto.country())
                .city(addressDto.city())
                .street(addressDto.street())
                .house(addressDto.house())
                .flat(addressDto.flat())
                .build();
    }

    @Override
    public AddressDto toAddressDto(Address address) {
        return AddressDto.builder()
                .country(address.getCountry())
                .city(address.getCity())
                .street(address.getStreet())
                .house(address.getHouse())
                .flat(address.getFlat())
                .build();
    }
}
