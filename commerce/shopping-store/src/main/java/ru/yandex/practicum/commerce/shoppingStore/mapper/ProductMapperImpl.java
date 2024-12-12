package ru.yandex.practicum.commerce.shoppingStore.mapper;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.commerce.api.dto.ProductDto;
import ru.yandex.practicum.commerce.shoppingStore.model.Product;

@Service
public class ProductMapperImpl implements ProductMapper {

    public Product toProduct(ProductDto productDto) {
        return Product.builder()
                .productId(productDto.productId())
                .name(productDto.productName())
                .description(productDto.description())
                .imageSrc(productDto.imageSrc())
                .quantityState(productDto.quantityState())
                .productState(productDto.productState())
                .rating(productDto.rating())
                .productCategory(productDto.productCategory())
                .price(productDto.price())
                .build();
    }

    public ProductDto toProductDto(Product product) {
        return ProductDto.builder()
                .productId(product.getProductId())
                .productName(product.getName())
                .description(product.getDescription())
                .imageSrc(product.getImageSrc())
                .quantityState(product.getQuantityState())
                .productState(product.getProductState())
                .rating(product.getRating())
                .productCategory(product.getProductCategory())
                .price(product.getPrice())
                .build();
    }

}
