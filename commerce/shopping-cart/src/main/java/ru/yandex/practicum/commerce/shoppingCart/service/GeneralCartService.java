package ru.yandex.practicum.commerce.shoppingCart.service;


import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.commerce.api.WarehouseClient;
import ru.yandex.practicum.commerce.api.dto.BookedProductsDto;
import ru.yandex.practicum.commerce.api.dto.ChangeProductQuantityRequest;
import ru.yandex.practicum.commerce.api.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.api.dto.exception.NoProductsInShoppingCartException;
import ru.yandex.practicum.commerce.api.dto.exception.NotAuthorizedUserException;
import ru.yandex.practicum.commerce.shoppingCart.mapper.CartMapper;
import ru.yandex.practicum.commerce.shoppingCart.model.CartProduct;
import ru.yandex.practicum.commerce.shoppingCart.model.CartProductId;
import ru.yandex.practicum.commerce.shoppingCart.model.ShoppingCart;
import ru.yandex.practicum.commerce.shoppingCart.repository.CartProductsRepository;
import ru.yandex.practicum.commerce.shoppingCart.repository.CartRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeneralCartService implements CartService {

    private final CartRepository cartRepository;
    private final CartProductsRepository cartProductsRepository;
    private final CartMapper cartMapper;
    private final WarehouseClient warehouseClient;

    @Override
    @Transactional(readOnly = true)
    public ShoppingCartDto get(String username) {
        checkUsernameForEmpty(username);
        Optional<ShoppingCart> shoppingCart = cartRepository.findByUsernameIgnoreCaseAndActivated(
                username, true);
        if (shoppingCart.isPresent()) {
            List<CartProduct> cartProductList =
                    cartProductsRepository.findAllByCartProductId_ShoppingCartId(shoppingCart.get().getShoppingCartId());
            return cartMapper.toShoppingCartDto(shoppingCart.get(), cartProductList);
        } else {
            throw new NotAuthorizedUserException("No cart for username: " + username);
        }
    }

    @Override
    @Transactional
    public ShoppingCartDto addProducts(String username, Map<UUID, Long> products) {
        checkUsernameForEmpty(username);

        Optional<ShoppingCart> shoppingCart =
                cartRepository.findByUsernameIgnoreCaseAndActivated(username, true);

        UUID cartId;

        if(shoppingCart.isPresent()) {
            cartId = shoppingCart.get().getShoppingCartId();
        } else {
            ShoppingCart newCart = ShoppingCart.builder()
                    .username(username)
                    .activated(true)
                    .build();
            ShoppingCart savedShoppingCart = cartRepository.save(newCart);
            cartId = savedShoppingCart.getShoppingCartId();
        }

        List<CartProduct> newCartProducts = new ArrayList<>();
        for (Map.Entry<UUID, Long> entry : products.entrySet()) {
            newCartProducts.add(
                    new CartProduct(
                        new CartProductId(cartId, entry.getKey()), entry.getValue()));
        }

        cartProductsRepository.saveAll(newCartProducts);

        return get(username);

    }

    @Override
    @Transactional
    public void deactivate(String username) {
        checkUsernameForEmpty(username);
        Optional<ShoppingCart> shoppingCart =
                cartRepository.findByUsernameIgnoreCaseAndActivated(username, true);
        if (shoppingCart.isPresent()) {
            shoppingCart.get().setActivated(false);
        } else {
            throw new NotAuthorizedUserException("No cart for username: " + username);
        }
    }

    @Override
    @Transactional
    public ShoppingCartDto update(String username, Map<UUID, Long> products) {
        checkUsernameForEmpty(username);
        ShoppingCartDto currentShoppingCart = get(username);
        UUID cartId = currentShoppingCart.shoppingCartId();

        for(Map.Entry<UUID, Long> entry : products.entrySet()) {
            if(currentShoppingCart.products().get(entry.getKey()) == null) {
                throw new NoProductsInShoppingCartException("No product " + entry.getKey()+ "in shoppingCart");
            }
        }

        List<CartProduct> currentCartProducts = mapToCartProducts(cartId, currentShoppingCart.products());


        cartProductsRepository.deleteAll(currentCartProducts);

        List<CartProduct> newCartProducts = mapToCartProducts(cartId, products);
        List<CartProduct> savedCartProducts = cartProductsRepository.saveAll(newCartProducts);

        return cartMapper.toShoppingCartDto(cartMapper.toShoppingCart(currentShoppingCart, username),
                savedCartProducts);
    }

    @Override
    @Transactional
    public ShoppingCartDto changeProductQuantity(String username,
                                                 ChangeProductQuantityRequest changeProductQuantityRequest) {
        checkUsernameForEmpty(username);
        ShoppingCartDto currentShoppingCart = get(username);

        log.info("Нужный объем продукта {}: {}",
                changeProductQuantityRequest.productId(), changeProductQuantityRequest.newQuantity());

        UUID cartId = currentShoppingCart.shoppingCartId();

        Optional<CartProduct> cartProduct = cartProductsRepository.findById(
                new CartProductId(cartId, changeProductQuantityRequest.productId()));


        if (cartProduct.isPresent()) {
            log.info("Существующая корзина пользователя {} найдена", username);
            CartProduct cartProductForSave = cartProduct.get();
            log.info("Текущий объем продукта {}: {}",
                    cartProductForSave.getCartProductId(), cartProductForSave.getQuantity());
            cartProductForSave.setQuantity(changeProductQuantityRequest.newQuantity());
            log.info("Измененный объем продукта {}: {}",
                    cartProduct.get().getCartProductId(), cartProduct.get().getQuantity());
            cartProductsRepository.save(cartProduct.get());
        } else {
            throw new NoProductsInShoppingCartException(
                    "Product with id " + changeProductQuantityRequest.productId() + " not found");
        }

        ShoppingCartDto savedShoppingCartDto = get(username);
        log.info("Измененный shopping cart: {}", savedShoppingCartDto);

        return savedShoppingCartDto;
    }

    @Override
    @Transactional
    public BookedProductsDto checkForProductsSufficiency(String username) {
        try {
            return warehouseClient.checkForProductsSufficiency(get(username));
        } catch (Exception e) {
            log.error("Ошибка при резервировании корзины");
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ShoppingCartDto getById(UUID cartId) {
        ShoppingCart shoppingCart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundException("Cart with id " + cartId + " not found"));
        List<CartProduct> cartProductList =
                cartProductsRepository.findAllByCartProductId_ShoppingCartId(cartId);
        return cartMapper.toShoppingCartDto(shoppingCart, cartProductList);
    }

    private void checkUsernameForEmpty(String username) {
        if(username == null || username.isBlank()) {
            throw new NotAuthorizedUserException("Username is empty");
        }
    }

    private List<CartProduct> mapToCartProducts(UUID cartId, Map<UUID, Long> products) {
        List<CartProduct> cartProducts = new ArrayList<>();
        for (Map.Entry<UUID, Long> entry : products.entrySet()) {
            cartProducts.add(new CartProduct(
                    new CartProductId(cartId, entry.getKey()), entry.getValue()));
        }
        return cartProducts;
    }

}
