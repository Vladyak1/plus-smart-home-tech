package ru.yandex.practicum.commerce.warehouse.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("warehouse.address")
@Getter @Setter
public class WarehouseAddress {
    private String country;
    private String city;
    private String street;
    private String house;
    private String flat;
}
