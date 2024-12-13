package ru.yandex.practicum.commerce.shoppingStore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.commerce.api.dto.ProductDto;
import ru.yandex.practicum.commerce.api.dto.SetProductQuantityStateRequest;
import ru.yandex.practicum.commerce.api.dto.enums.ProductCategory;
import ru.yandex.practicum.commerce.api.dto.exception.ProductAlreadyExistException;
import ru.yandex.practicum.commerce.api.dto.exception.ProductNotFoundException;
import ru.yandex.practicum.commerce.shoppingStore.mapper.ProductMapper;
import ru.yandex.practicum.commerce.shoppingStore.model.Product;
import ru.yandex.practicum.commerce.shoppingStore.repository.ProductRepository;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class GeneralProductService implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDto> getAll(ProductCategory category, Pageable pageable) {

        return productRepository.findByProductCategory(category, pageable).stream()
                .map(productMapper::toProductDto)
                .toList();
    }

    @Override
    public ProductDto create(ProductDto productDto) {
        if (productRepository.findById(productDto.productId()).isPresent()) {
            throw new ProductAlreadyExistException(productDto.productId().toString());
        }
        log.info("Product for save: {}", productMapper.toProduct(productDto));
        Product productSaved = productRepository.save(
                productMapper.toProduct(productDto));
        log.info("Saved product: {}", productSaved);
        return productMapper.toProductDto(productSaved);
    }

    @Override
    public ProductDto get(UUID productId) {
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new ProductNotFoundException("Product with id " + productId + " not found"));

        return productMapper.toProductDto(product);

    }

    @Override
    public ProductDto update(ProductDto productDto) {
        productRepository.findById(productDto.productId()).
                orElseThrow(() -> new ProductNotFoundException(
                        "Product with id " + productDto.productId() + " not found"));
        productRepository.deleteById(productDto.productId());
        Product productSaved = productRepository.save(
                productMapper.toProduct(productDto)
        );
        return productMapper.toProductDto(productSaved);
    }

    @Override
    public boolean isDeleted(UUID productId) {
        return productRepository.existsById(productId);
    }

    @Override
    public boolean changeState(SetProductQuantityStateRequest stateRequest) {
        Product productInDb = productRepository.findById(stateRequest.productId()).
                orElseThrow(() -> new ProductNotFoundException(
                        "Product with id " + stateRequest.productId() + " not found"));
        try {
            productRepository.save(productInDb);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public List<ProductDto> getProductsByIds(List<UUID> ids) {
        return productRepository.findAllByIdIn(ids);
    }


}
