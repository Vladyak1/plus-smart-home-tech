package ru.yandex.practicum.commerce.order.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "addressId") @ToString
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    UUID addressId;
    String country;
    String city;
    String street;
    String house;
    String flat;
}
