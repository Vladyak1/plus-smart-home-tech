package ru.yandex.practicum.commerce.shoppingStore;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.commerce.api.dto.ProductDto;
import ru.yandex.practicum.commerce.api.dto.SetProductQuantityStateRequest;
import ru.yandex.practicum.commerce.api.dto.enums.ProductCategory;
import ru.yandex.practicum.commerce.shoppingStore.service.ProductService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shopping-store")
public class StoreController {

    private static final Logger log = LoggerFactory.getLogger(StoreController.class);
    private final ProductService productService;

    @GetMapping
    public List<ProductDto> getAllProducts(@RequestParam ProductCategory category,
                                           Pageable pageable) {
        log.info("==> GET /api/v1/shopping-store. Getting all products by category {}, {}",
                category, pageable);

        log.info("==> GET /api/v1/shopping-store. Getting all products by category {}, {}", category, pageable);

        List<ProductDto> receivedList = productService.getAll(category, pageable);
        log.info("<== GET /api/v1/shopping-store. Received product list with size {}", receivedList.size());
        return receivedList;
    }

    @PutMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        log.info("==> PUT /api/v1/shopping-store. Creating product {}", productDto);
        ProductDto savedProductDto = productService.create(productDto);
        log.info("<== PUT /api/v1/shopping-store. Created product {}", productDto);
        return savedProductDto;
    }

    @PostMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        log.info("==> POST /api/v1/shopping-store. Updating product {}", productDto);
        ProductDto updatedProductDto = productService.update(productDto);
        log.info("<== POST /api/v1/shopping-store. Updated product {}", productDto);
        return updatedProductDto;
    }

    @PostMapping("/removeProductFromStore")
    public boolean removeProduct(@RequestParam UUID productId) {
        log.info("==> POST /api/v1/shopping-store/removeProductFromStore. Removing product {}", productId);
        boolean isProductDeleted = productService.isDeleted(productId);
        log.info("<== POST /api/v1/shopping-store/removeProductFromStore. Is product removed: {}", isProductDeleted);
        return isProductDeleted;
    }

    @PostMapping("/quantityState")
    public boolean changeProductState(@RequestParam SetProductQuantityStateRequest stateRequest) {
        log.info("==> POST /api/v1/shopping-store/quantityState. Changing product state {}", stateRequest);
        boolean isProductChangeState = productService.changeState(stateRequest);
        log.info("<== POST /api/v1/shopping-store/quantityState. Is product change state {}", isProductChangeState);
        return isProductChangeState;
    }


    @GetMapping("/{productId}")
    public ProductDto getProduct(@PathVariable UUID productId) {
        log.info("==> GET /api/v1/shopping-store/{}. Getting product with productId: {}", productId, productId);
        ProductDto receivedProductDto = productService.get(productId);
        log.info("<== GET /api/v1/shopping-store/{}. Getting product with productId: {}", productId, productId);
        return receivedProductDto;
    }





}
