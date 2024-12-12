package ru.yandex.practicum.commerce.warehouse.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.commerce.api.ShoppingStoreClient;
import ru.yandex.practicum.commerce.api.dto.*;
import ru.yandex.practicum.commerce.api.dto.enums.QuantityState;
import ru.yandex.practicum.commerce.api.dto.exception.NoSpecifiedProductInWareHouseException;
import ru.yandex.practicum.commerce.api.dto.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.commerce.api.dto.exception.ProductNotFoundException;
import ru.yandex.practicum.commerce.api.dto.exception.SpecifiedProductAlreadyInWarehouseException;
import ru.yandex.practicum.commerce.warehouse.mapper.BookedProductsMapper;
import ru.yandex.practicum.commerce.warehouse.mapper.DimensionMapper;
import ru.yandex.practicum.commerce.warehouse.model.Dimension;
import ru.yandex.practicum.commerce.warehouse.model.OrderBooking;
import ru.yandex.practicum.commerce.warehouse.model.WarehouseAddress;
import ru.yandex.practicum.commerce.warehouse.model.WarehouseProduct;
import ru.yandex.practicum.commerce.warehouse.repository.OrderBookingRepository;
import ru.yandex.practicum.commerce.warehouse.repository.WarehouseProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional (readOnly = true)
public class GeneralWarehouseService implements WarehouseService {

    private final WarehouseProductRepository warehouseProductRepository;
    private final OrderBookingRepository orderBookingRepository;
    private final DimensionMapper dimensionMapper;
    private final BookedProductsMapper bookedProductsMapper;
    private final ShoppingStoreClient shoppingStoreClient;

    private static final long PRODUCT_LIMIT = 150;

    @Override
    public void addNewProduct(NewProductInWarehouseRequest newProduct) {
        if (warehouseProductRepository.findById(newProduct.productId()).isPresent()) {
            throw new SpecifiedProductAlreadyInWarehouseException("Product already exists in warehouse");
        }

        Dimension dimension = dimensionMapper.fromDto(newProduct.dimension());

        WarehouseProduct warehouseProduct = WarehouseProduct.builder()
                .productId(newProduct.productId())
                .fragile(newProduct.fragile())
                .dimension(dimension)
                .weight(newProduct.weight())
                .quantity(0L)
                .build();

        warehouseProductRepository.save(warehouseProduct);
    }

    @Override
    public void returnProducts(Map<UUID, Long> products) {
        List<WarehouseProduct> warehouseProductList = warehouseProductRepository.findAllByProductIdIn(
                    products.keySet().stream()
                            .toList());
        if (products.size() != warehouseProductList.size()) {
            throw new ProductNotFoundException("Not all product found in warehouse");
        }

        Map<UUID, WarehouseProduct> productMap = warehouseProductList.stream()
                .collect(Collectors.toMap(WarehouseProduct::getProductId, product -> product));

        for (Map.Entry<UUID, Long> entry : products.entrySet()) {
            UUID productId = entry.getKey();
            WarehouseProduct product = productMap.get(productId);
            product.setQuantity(product.getQuantity() + entry.getValue());
        }
        List<WarehouseProduct> savedProducts = warehouseProductRepository.saveAll(productMap.values());
        log.info("Returning products with id: {}, by quantity {}. Products with new quantity: {}",
                products.keySet(), products.entrySet(), savedProducts);
    }

    @Override
    @Transactional
    public BookedProductsDto assemblyProductsForOrder(AssemblyProductForOrderRequest productsForAssemblyRequest) {

        Map<UUID, Long> productsForAssembly = productsForAssemblyRequest.products();

        List<WarehouseProduct> warehouseProductList = warehouseProductRepository.findAllByProductIdIn(
                productsForAssembly.keySet().stream()
                        .toList()
        );

        Map<UUID, WarehouseProduct> warehouseProductMap = warehouseProductList.stream()
                .collect(Collectors.toMap(WarehouseProduct::getProductId, product -> product));

        checkForExistingAndEnoughQuantity(productsForAssembly, warehouseProductMap);

        List<OrderBooking> orderBookingList = new ArrayList<>();
        for (Map.Entry<UUID, Long> entry : productsForAssembly.entrySet()) { // Для каждого товара из заказа:
            OrderBooking orderBooking = OrderBooking.builder() //Создание продукта в сборке
                    .orderId(productsForAssemblyRequest.orderId())
                    .productId(entry.getKey())
                    .reservedQuantity(entry.getValue())
                    .build();
            orderBookingList.add(orderBooking); //Добавление в перечень
        }

        //Сохранение собранного товаров
        List<OrderBooking> saveOrderBookingList = orderBookingRepository.saveAll(orderBookingList);

        //возвращение списка собранных товаров
        return bookedProductsMapper.toBookedProductsDto(saveOrderBookingList, warehouseProductMap);

    }

    @Override
    public BookedProductsDto checkForProductsSufficiency(ShoppingCartDto shoppingCart) {
        Map<UUID, Long> productsForChecking = shoppingCart.products();

        List<WarehouseProduct> warehouseProductList = warehouseProductRepository.findAllByProductIdIn(
                productsForChecking.keySet().stream()
                        .toList()
        );

        Map<UUID, WarehouseProduct> warehouseProductMap = warehouseProductList.stream()
                .collect(Collectors.toMap(WarehouseProduct::getProductId, product -> product));

        checkForExistingAndEnoughQuantity(productsForChecking, warehouseProductMap);

        return bookedProductsMapper.toCheckedBookedProductsDto(productsForChecking,
                warehouseProductMap.values().stream().toList());
    }

    @Override
    @Transactional
    public void addingProductsQuantity(AddProductToWarehouseRequest addingProductsQuantity) {
        WarehouseProduct warehouseProduct =
                warehouseProductRepository.findByProductId(addingProductsQuantity.productId())
                        .orElseThrow(() -> new NoSpecifiedProductInWareHouseException(
                                "Product with id: " + addingProductsQuantity.productId() + " not found in warehouse"));
        warehouseProduct.setQuantity(warehouseProduct.getQuantity() + addingProductsQuantity.quantity());
        WarehouseProduct saveWarehouseProduct = warehouseProductRepository.save(warehouseProduct);

        shoppingStoreClient.changeProductState(
                new SetProductQuantityStateRequest(
                        saveWarehouseProduct.getProductId(),
                        specifyQuantityState(saveWarehouseProduct.getQuantity())));
        log.info("Added product with id: {}, by quantity {}. New quantity: {}",
                addingProductsQuantity.productId(),
                addingProductsQuantity.quantity(),
                saveWarehouseProduct.getQuantity());
    }

    @Override
    public AddressDto getWarehouseAddress() {
        WarehouseAddress warehouseAddress = new WarehouseAddress();
        return AddressDto.builder()
                .country(warehouseAddress.getCountry())
                .city(warehouseAddress.getCity())
                .street(warehouseAddress.getStreet())
                .house(warehouseAddress.getHouse())
                .flat(warehouseAddress.getFlat())
                .build();
    }

    @Override
    public void shipToDelivery(ShippedToDeliveryRequest shippedToDeliveryRequest) {
        List<OrderBooking> orderBookingList =
                orderBookingRepository.findAllOrderBookingByOrderId(
                        shippedToDeliveryRequest.orderId());
        for (OrderBooking orderBooking : orderBookingList) {
            orderBooking.setDeliveryId(shippedToDeliveryRequest.deliveryId());
        }
        orderBookingRepository.saveAll(orderBookingList);
    }

    private QuantityState specifyQuantityState(long quantity) {
        if(quantity == 0) {
            return QuantityState.ENDED;
        } else if(quantity >= PRODUCT_LIMIT * 0.75) {
            return QuantityState.MANY;
        } else if (quantity >= PRODUCT_LIMIT * 0.5) {
            return QuantityState.ENOUGH;
        } else {
            return QuantityState.FEW;
        }

    }

    private void checkForExistingAndEnoughQuantity(Map<UUID, Long> productsForChecking,
                                                   Map<UUID, WarehouseProduct> warehouseProductMap) {
        for (Map.Entry<UUID, Long> entry : productsForChecking.entrySet()) {
            UUID checkingProductId = entry.getKey();

            if(warehouseProductMap.get(checkingProductId) == null) {
                throw new NoSpecifiedProductInWareHouseException("No Product in warehouse: " + checkingProductId);
            } else if(warehouseProductMap.get(checkingProductId).getQuantity() < entry.getValue()) {
                throw new ProductInShoppingCartLowQuantityInWarehouse(
                        "Not enough product " + checkingProductId + " + in warehouse");
            }
        }

    }

}
