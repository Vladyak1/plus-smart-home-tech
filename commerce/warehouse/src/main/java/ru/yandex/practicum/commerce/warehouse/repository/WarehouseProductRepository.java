package ru.yandex.practicum.commerce.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.commerce.warehouse.model.WarehouseProduct;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WarehouseProductRepository extends JpaRepository<WarehouseProduct, UUID> {

    List<WarehouseProduct> findAllByProductIdIn(List<UUID> productId);

    Optional<WarehouseProduct> findByProductId(UUID productId);
}
