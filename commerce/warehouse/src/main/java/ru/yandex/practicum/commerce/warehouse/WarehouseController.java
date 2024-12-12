package ru.yandex.practicum.commerce.warehouse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.commerce.api.dto.*;
import ru.yandex.practicum.commerce.warehouse.service.WarehouseService;

import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/warehouse")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PutMapping
    public void addNewProduct(@RequestBody NewProductInWarehouseRequest newProduct) {
        log.info("==> PUT /api/v1/warehouse Add new product in warehouse: {}", newProduct);
        warehouseService.addNewProduct(newProduct);
        log.info("||| PUT /api/v1/warehouse. Added new product in warehouse");
    }

    @PostMapping("/return")
    public void returnProducts(Map<UUID, Long> products) {
        log.info("==> POST /api/v1/warehouse/return. Returning products: {}", products);
        warehouseService.returnProducts(products);
        log.info("||| POST /api/v1/warehouse/return. Products returned");
    }

    @PostMapping("/check")
    public BookedProductsDto checkForProductsSufficiency(@RequestBody ShoppingCartDto shoppingCart) {
        log.info("==> POST /api/v1/warehouse/check. Check products sufficiency: {}", shoppingCart);
        BookedProductsDto bookedProducts = warehouseService.checkForProductsSufficiency(shoppingCart);
        log.info("<== POST /api/v1/warehouse/check. Checked Booked products: {}", bookedProducts);
        return bookedProducts;
    }

    @PostMapping("/assembly")
    public BookedProductsDto assemblyProductForOrder(AssemblyProductForOrderRequest productForOrderRequest) {
        log.info("==> POST /api/v1/warehouse/assembly. Assembly product for order (new method): {}", productForOrderRequest);
        BookedProductsDto assemblyProducts = warehouseService.assemblyProductsForOrder(productForOrderRequest);
        log.info("<== POST /api/v1/warehouse/assembly. " +
                "Assembly product for order (new method) completed: {}", productForOrderRequest);
        return assemblyProducts;
    }


    @PostMapping("/add")
    public void addQuantityOfProduct(@RequestBody AddProductToWarehouseRequest addingProductsQuantity) {
        log.info("==> POST /api/v1/warehouse/add. Add quantity of product to warehouse: {}", addingProductsQuantity);
        warehouseService.addingProductsQuantity(addingProductsQuantity);
        log.info("||| POST /api/v1/warehouse/add. Add quantity of product to warehouse: completed");
    }

    @GetMapping("/address")
    public AddressDto getWarehouseAddress() {
        log.info("==> GET /api/v1/warehouse/address. Getting warehouse address");
        AddressDto addressDto = warehouseService.getWarehouseAddress();
        log.info("<== GET /api/v1/warehouse/address. Warehouse address: {}", addressDto);
        return addressDto;
    }

    @PostMapping("/shipped")
    public void shipToDelivery(ShippedToDeliveryRequest shippedToDeliveryRequest) {
        log.info("==> GET /api/v1/warehouse/shipped. Change order status to <On Delivery>");
        warehouseService.shipToDelivery(shippedToDeliveryRequest);

        log.info("==> GET /api/v1/warehouse/address. Change order status to <On Delivery>");

    }



}
