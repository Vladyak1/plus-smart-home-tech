package ru.yandex.practicum.commerce.delivery.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import ru.yandex.practicum.commerce.api.dto.enums.DeliveryState;

import java.util.UUID;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "deliveryId") @ToString
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "deliveries")
public class Delivery {

    @Id
    @UuidGenerator
    private UUID deliveryId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_address_id")
    private Address fromAddress;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_address_id")
    private Address toAddress;

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "deliveryState")
    @Enumerated(EnumType.STRING)
    private DeliveryState deliveryState;


}
