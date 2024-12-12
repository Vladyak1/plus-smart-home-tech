package ru.yandex.practicum.commerce.warehouse.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter @Setter @ToString
@Builder
@NoArgsConstructor @AllArgsConstructor
@Table(name = "order_bookings_products")
public class OrderBooking {

    @Id
    @UuidGenerator
    @Column(name = "order_product_id")
    private UUID assembly_product_id;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "delivery_id")
    private UUID deliveryId;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "reserved_quantity", nullable = false)
    private long reservedQuantity;

}
