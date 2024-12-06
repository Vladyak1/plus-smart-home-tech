package ru.yandex.practicum.commerce.shoppingCart.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter @Setter @EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartProductId implements Serializable {
    private UUID shoppingCartId;
    private UUID productId;
}
