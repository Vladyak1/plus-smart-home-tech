package ru.yandex.practicum.commerce.shoppingCart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.commerce.shoppingCart.model.CartProduct;
import ru.yandex.practicum.commerce.shoppingCart.model.CartProductId;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartProductsRepository extends JpaRepository<CartProduct, CartProductId> {

    List<CartProduct> findAllByCartProductId_ShoppingCartId(UUID shoppingCartId);

}
