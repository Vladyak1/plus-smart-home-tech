package ru.yandex.practicum.commerce.shoppingStore.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.commerce.api.dto.enums.ProductCategory;
import ru.yandex.practicum.commerce.shoppingStore.model.Product;

import java.util.List;
import java.util.UUID;


public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findByProductCategory(ProductCategory category, Pageable pageable);

}
