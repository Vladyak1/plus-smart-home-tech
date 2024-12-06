package ru.yandex.practicum.commerce.shoppingCart.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter @Setter @ToString
@EqualsAndHashCode(of = "shoppingCartId")
@Builder
@RequiredArgsConstructor @AllArgsConstructor
@Table(name = "shopping_carts")
public class ShoppingCart {

    @Id
    @Column(name = "shoppingCart_id", nullable = false)
    @UuidGenerator
    private UUID shoppingCartId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "activated", nullable = false)
    private boolean activated;

}
