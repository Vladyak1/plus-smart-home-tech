package ru.yandex.practicum.commerce.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.commerce.api.dto.SetProductQuantityStateRequest;

@FeignClient(name = "shopping-store", path = "/api/v1/shopping-store")
public interface ShoppingStoreClient {

    @PostMapping("/quantityState")
    boolean changeProductState(@RequestParam SetProductQuantityStateRequest stateRequest);

}
