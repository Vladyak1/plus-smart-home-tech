package ru.yandex.practicum.commerce.api;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.commerce.api.dto.*;

import java.util.Map;
import java.util.UUID;

@FeignClient(name = "warehouse", path = "/api/v1/warehouse")
public interface WarehouseClient {

    @PostMapping("/shipped")
    void shipToDelivery(ShippedToDeliveryRequest shippedToDeliveryRequest);

    @PostMapping("/booking")
    BookedProductsDto checkForProductsSufficiency(@RequestBody ShoppingCartDto shoppingCart);

    @PostMapping("/assembly")
    BookedProductsDto assemblyProductsForOrder(AssemblyProductForOrderRequest assembly);

    @GetMapping("/address")
    AddressDto getWarehouseAddress();

    @PostMapping("/return")
    void returnProducts(Map<UUID, Long> products);

}
