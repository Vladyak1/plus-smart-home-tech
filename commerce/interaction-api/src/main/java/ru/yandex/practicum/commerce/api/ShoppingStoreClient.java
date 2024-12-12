package ru.yandex.practicum.commerce.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.commerce.api.dto.ProductDto;
import ru.yandex.practicum.commerce.api.dto.SetProductQuantityStateRequest;

import java.util.UUID;

@FeignClient(name = "shopping-store", path = "/api/v1/shopping-store")
public interface ShoppingStoreClient {

    @GetMapping("/{productId}")
    ProductDto getProduct(@PathVariable UUID productId);

    @PostMapping("/quantityState")
    boolean changeProductState(@RequestParam SetProductQuantityStateRequest stateRequest);

}