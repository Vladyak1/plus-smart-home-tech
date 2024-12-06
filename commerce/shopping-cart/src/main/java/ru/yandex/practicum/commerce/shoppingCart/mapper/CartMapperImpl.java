package ru.yandex.practicum.commerce.shoppingCart.mapper;


import org.springframework.stereotype.Service;
import ru.yandex.practicum.commerce.api.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.shoppingCart.model.CartProduct;
import ru.yandex.practicum.commerce.shoppingCart.model.ShoppingCart;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartMapperImpl implements CartMapper {

    @Override
    public ShoppingCart toShoppingCart(ShoppingCartDto shoppingCartDto, String username) {
        return ShoppingCart.builder()
                .shoppingCartId(UUID.fromString(shoppingCartDto.shoppingCartId()))
                .username(username)
                .build();
    }

    @Override
    public ShoppingCartDto toShoppingCartDto(ShoppingCart shoppingCart, List<CartProduct> products) {
        Map<String, Long> productsMap = products.stream()
                .collect(Collectors.toMap(cartProduct -> cartProduct.getCartProductId().getProductId().toString(), CartProduct::getQuantity));

        return ShoppingCartDto.builder()
                .shoppingCartId(shoppingCart.getShoppingCartId().toString())
                .products(productsMap)
                .build();
    }
}
