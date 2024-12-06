package ru.yandex.practicum.commerce.shoppingCart.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter @Setter @ToString
@Builder
@RequiredArgsConstructor @AllArgsConstructor
@Table(name = "carts_products")
public class CartProduct {

    @EmbeddedId
    private CartProductId cartProductId;

    @Column(name = "quantity")
    private long quantity;

}
