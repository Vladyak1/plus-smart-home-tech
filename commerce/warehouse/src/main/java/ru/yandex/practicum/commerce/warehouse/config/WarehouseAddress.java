package ru.yandex.practicum.commerce.warehouse.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("warehouse.address")
@Getter @Setter
public class WarehouseAddress {
    String country;
    String city;
    String street;
    String house;
    String flat;
}
