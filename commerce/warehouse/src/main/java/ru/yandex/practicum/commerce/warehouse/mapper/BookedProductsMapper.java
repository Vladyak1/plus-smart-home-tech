package ru.yandex.practicum.commerce.warehouse.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.commerce.api.dto.BookedProductsDto;
import ru.yandex.practicum.commerce.warehouse.model.Dimension;
import ru.yandex.practicum.commerce.warehouse.model.ReservedProduct;
import ru.yandex.practicum.commerce.warehouse.model.WarehouseProduct;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class BookedProductsMapper {

    public BookedProductsDto toBookedProductsDto(List<ReservedProduct> reservedProductList,
                                          Map<UUID, WarehouseProduct> warehouseProductList) {
        double bookedVolume = 0.00;
        double bookedWeight = 0.00;
        boolean isBookedProductsFragile = false;

        for (ReservedProduct reservedProduct : reservedProductList) {
            UUID reservedProductId = reservedProduct.getProductId();
            WarehouseProduct warehouseProduct = warehouseProductList.get(reservedProductId);
            Dimension prodDimension = warehouseProduct.getDimension();
            double prodWeight = warehouseProduct.getWeight();

            double productVolume =
                    reservedProduct.getReservedQuantity() *
                            (prodDimension.getDepth() * prodDimension.getHeight() * prodDimension.getWidth());
            bookedVolume += productVolume;

            double productWeight = reservedProduct.getReservedQuantity() * prodWeight;
            bookedWeight += productWeight;

            if (warehouseProduct.isFragile()) {
                isBookedProductsFragile = true;
            }
        }

        return BookedProductsDto.builder()
                .deliveryVolume(bookedVolume)
                .deliveryWeight(bookedWeight)
                .fragile(isBookedProductsFragile)
                .build();
    }
}
