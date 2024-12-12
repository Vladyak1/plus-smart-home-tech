package ru.yandex.practicum.commerce.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.commerce.api.dto.ShoppingCartDto;

import java.util.UUID;

@FeignClient(name = "shopping-cart", path = "/api/v1/shopping-cart")
public interface ShoppingCartClient {

    @GetMapping("/{cartId}")
    ShoppingCartDto getShoppingCartById(@PathVariable UUID cartId);

    @GetMapping
    ShoppingCartDto get(@RequestParam String username);

}
