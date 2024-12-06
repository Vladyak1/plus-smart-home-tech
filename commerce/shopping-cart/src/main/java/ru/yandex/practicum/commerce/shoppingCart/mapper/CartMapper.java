package ru.yandex.practicum.commerce.shoppingCart.mapper;

import ru.yandex.practicum.commerce.api.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.shoppingCart.model.CartProduct;
import ru.yandex.practicum.commerce.shoppingCart.model.ShoppingCart;

import java.util.List;

public interface CartMapper {

    ShoppingCart toShoppingCart(ShoppingCartDto shoppingCartDto, String username);

    ShoppingCartDto toShoppingCartDto(ShoppingCart shoppingCart, List<CartProduct> products);
}
