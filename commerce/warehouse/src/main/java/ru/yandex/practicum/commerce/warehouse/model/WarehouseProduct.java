package ru.yandex.practicum.commerce.warehouse.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Table(name = "warehouse_products")
public class WarehouseProduct {

    @Id
    @UuidGenerator
    @Column(name = "warehouse_product_id", nullable = false)
    private UUID warehouseProductId;

    @Column(name = "fragile")
    private boolean fragile;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dimension_id", referencedColumnName = "dimension_id", nullable = false)
    private Dimension dimension;

    @Column(name = "weight", nullable = false)
    private double weight;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "quantity")
    private long quantity;

}
