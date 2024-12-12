package ru.yandex.practicum.commerce.warehouse.service;

import ru.yandex.practicum.commerce.api.dto.*;

import java.util.Map;
import java.util.UUID;

public interface WarehouseService {

    void addNewProduct(NewProductInWarehouseRequest newProduct);

    void returnProducts(Map<UUID, Long> products);

    BookedProductsDto assemblyProductsForOrder(AssemblyProductForOrderRequest assembly);

    BookedProductsDto checkForProductsSufficiency(ShoppingCartDto shoppingCart);

    void addingProductsQuantity(AddProductToWarehouseRequest addingProductsQuantity);

    AddressDto getWarehouseAddress();

    void shipToDelivery(ShippedToDeliveryRequest shippedToDeliveryRequest);

}
