package ru.yandex.practicum.commerce.api.dto;

import lombok.Builder;

// Представление адреса в системе.
@Builder
public record AddressDto(

        String country,
        String city,
        String street,
        String house,
        String flat

) {
}
