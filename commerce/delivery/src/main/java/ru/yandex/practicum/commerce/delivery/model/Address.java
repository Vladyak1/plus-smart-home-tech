package ru.yandex.practicum.commerce.delivery.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "address_id") @ToString
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @UuidGenerator
    private UUID address_id;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "house")
    private String house;

    @Column(name = "flat")
    private String flat;
}
