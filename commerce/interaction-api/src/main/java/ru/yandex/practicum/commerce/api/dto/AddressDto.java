package ru.yandex.practicum.commerce.api.dto;

import lombok.Builder;

@Builder
public record AddressDto(

        String country,
        String city,
        String street,
        String house,
        String flat

) {
}
