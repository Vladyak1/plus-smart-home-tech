package ru.yandex.practicum.commerce.shoppingStore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import ru.yandex.practicum.commerce.api.dto.enums.ProductCategory;
import ru.yandex.practicum.commerce.api.dto.enums.ProductState;
import ru.yandex.practicum.commerce.api.dto.enums.QuantityState;

import java.util.UUID;

@Entity
@Table(name = "products")
@Getter @Setter @ToString
@EqualsAndHashCode(of = "productId")
@Builder
@RequiredArgsConstructor @AllArgsConstructor
public class Product {

    @Id
    @UuidGenerator
    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image_src", nullable = false)
    private String imageSrc;

    @Enumerated(EnumType.STRING)
    @Column(name = "quantity_state")
    private QuantityState quantityState;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_state")
    private ProductState productState;

    @Column(name = "rating")
    private double rating;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private ProductCategory productCategory;

    @Min(1)
    @Column(name = "price", nullable = false)
    private float price;

}
