package ru.yandex.practicum.commerce.shoppingStore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    UUID productId;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "description", nullable = false)
    String description;

    @Column(name = "image_src", nullable = false)
    String imageSrc; // Ссылка на картинку во внешнем хранилище или SVG

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name = "quantity_state")
    QuantityState quantityState; // Статус, перечисляющий состояние остатка как свойства товара

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name = "product_state")
    ProductState productState; // Статус товара

    @Column(name = "rating")
    double rating; // Рейтинг товара на основе оценок пользователей

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    ProductCategory productCategory; // Категория товара

    @Min(1)
    @Column(name = "price", nullable = false)
    float price; // Цена товара

}
