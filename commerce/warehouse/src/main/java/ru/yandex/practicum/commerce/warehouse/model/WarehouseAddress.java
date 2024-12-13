package ru.yandex.practicum.commerce.warehouse.model;

import lombok.Getter;
import lombok.Setter;

import java.security.SecureRandom;
import java.util.Random;

@Getter @Setter
public class WarehouseAddress {

    private static final String[] ADDRESSES =
            new String[] {"ADDRESS_1", "ADDRESS_2"};

    private static final String CURRENT_ADDRESS =
            ADDRESSES[Random.from(new SecureRandom()).nextInt(0, 1)];

    String country;
    String city;
    String street;
    String house;
    String flat;

    public WarehouseAddress() {
        String currentAddress = CURRENT_ADDRESS;
        country = currentAddress;
        city = currentAddress;
        street = currentAddress;
        house = currentAddress;
        flat = currentAddress;
    }
}
