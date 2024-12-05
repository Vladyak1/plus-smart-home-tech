package ru.yandex.practicum.commerce.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.commerce.warehouse.model.ReservedProduct;

import java.util.List;
import java.util.UUID;

public interface ReservedProductsRepository extends JpaRepository<ReservedProduct, UUID> {

    List<ReservedProduct> findAllByShoppingCartId(UUID shoppingCartId);

}
