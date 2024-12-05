package ru.yandex.practicum.commerce.shoppingCart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.commerce.shoppingCart.model.ShoppingCart;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<ShoppingCart, UUID> {

    Optional<ShoppingCart> findByUsernameIgnoreCaseAndActivated(String username, boolean isActivated);

    boolean existsByUsernameIgnoreCaseAndActivated(String username, boolean activated);

}
