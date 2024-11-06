package com.returdev.product.services.product;

import com.returdev.product.entities.DimensionsEntity;
import com.returdev.product.entities.ProductEntity;
import com.returdev.product.exceptions.InvalidIdentifierException;
import com.returdev.product.repositories.DimensionsRepository;
import com.returdev.product.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

/**
 * Implementation of the ProductService interface for managing products in the system.
 */
@Validated
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final DimensionsRepository dimensionsRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ProductEntity> getProductById(
            @NotNull(message = "${validation.not_null.message}") Long id
    ) {

        if (id == null) {
            throw new InvalidIdentifierException("exception.id_is_null.message");
        }

        return productRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductEntity> getProductByCode(
            @NotBlank(message = "${validation.not_blank.message}")
            @Size(min = 3, max = 20, message = "${validation.size.message}")
            String code,
            boolean includeHidden,
            Pageable pageable
    ) {
        return productRepository.findProductsByCode(code, includeHidden, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductEntity> getProductByBarCode(
            @NotBlank(message = "${validation.not_blank.message}")
            @Size(min = 8, max = 30, message = "${validation.size.message}")
            String barcode,
            boolean includeHidden,
            Pageable pageable
    ) {
        return productRepository.findProductsByBarcode(barcode, includeHidden, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductEntity> getProductByNameContaining(
            @NotBlank(message = "${validation.not_blank.message}") String name,
            boolean includeHidden,
            Pageable pageable
    ) {
        return productRepository.findProductsByNameContaining(name, includeHidden, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductEntity> getProductByNameStartingWith(
            @NotBlank(message = "${validation.not_blank.message}") String name,
            boolean includeHidden,
            Pageable pageable
    ) {
        return productRepository.findProductsByNameStartingWith(name, includeHidden, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductEntity> getProductsBySupplierId(Long supplierId, boolean includeHidden, Pageable pageable) {
        return productRepository.findProductsBySupplierId(supplierId, includeHidden, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductEntity> getProductsByCategoryId(Long categoryId, boolean includeHidden, Pageable pageable) {
        return productRepository.findProductsByCategoryId(categoryId, includeHidden, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductEntity> getProductsByModelId(Long modelId, boolean includeHidden, Pageable pageable) {
        return productRepository.findProductsByModelId(modelId, includeHidden, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductEntity> getAllProducts(boolean includeHidden, Pageable pageable) {

        if (includeHidden) {
            return productRepository.findAll(pageable);
        } else {
            return productRepository.findAllVisibleProducts(pageable);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductEntity> searchProducts(ProductEntity productEntity, boolean includeHidden, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        matcher.withIgnoreNullValues();

        if (includeHidden) {
            matcher.withIgnorePaths("isHidden");
        }

        return productRepository.findAll(Example.of(productEntity, matcher), pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public ProductEntity updateProduct(ProductEntity product) {

        if (product.getId() == null) {
            throw new InvalidIdentifierException("exception.id_is_null.message");
        }

        if (!productRepository.existsById(product.getId())) {
            throw new InvalidIdentifierException("exception.not_exists_by_id.message");
        }

        return productRepository.save(product);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ProductEntity> updateProductName(
            @NotNull(message = "${validation.not_null.message}") Long productId,
            @NotBlank(message = "${validation.not_blank.message}")
            @Size(min = 3, max = 50, message = "${validation.size.message}")
            String newName
    ) {
        return productRepository.updateProductName(productId, newName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ProductEntity> updateProductSummary(
            @NotNull(message = "${validation.not_null.message}") Long productId,
            @NotNull(message = "${validation.not_null.message}")
            @Size(max = 150, message = "${validation.size.max.message}")
            String newSummary
    ) {
        return productRepository.updateProductSummary(productId, newSummary);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ProductEntity> updateProductCode(
            @NotNull(message = "${validation.not_null.message}") Long productId,
            @NotEmpty(message = "${validation.not_empty.message}")
            @Size(min = 3, max = 20, message = "${validation.size.message}")
            String newCode
    ) {
        return productRepository.updateProductCode(productId, newCode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ProductEntity> updateProductBarcode(
            @NotNull(message = "${validation.not_null.message}") Long productId,
            @NotNull(message = "${validation.not_null.message}")
            @Size(min = 8, max = 30, message = "${validation.size.message}")
            String barcode
    ) {
        return productRepository.updateProductBarcode(productId, barcode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ProductEntity> updateProductModel(
            @NotNull(message = "${validation.not_null.message}") Long productId,
            @NotNull(message = "${validation.not_null.message}") Long modelId
    ) {
        return productRepository.updateProductModel(productId, modelId);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Optional<ProductEntity> updateProductDimensions(
            @NotNull(message = "${validation.not_null.message}") Long productId,
            @NotNull(message = "${validation.not_null.message}")
            @Valid DimensionsEntity dimensions
    ) {

        Long savedDimensionId = dimensionsRepository.save(dimensions).getId();

        return productRepository.updateProductDimensions(productId, savedDimensionId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addProductSupplier(Long productId, Long newSupplierId) {
        int result = productRepository.addProductSupplier(productId, newSupplierId);

        if (result == 0){
            throw new EntityNotFoundException();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeProductSupplier(Long productId, Long supplierId) {
        int result = productRepository.deleteProductSupplier(productId, supplierId);

        if (result == 0){
            throw new EntityNotFoundException();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hideProduct(Long productId) {
        int result = productRepository.hideProduct(productId);

        if (result == 0){
            throw new EntityNotFoundException();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unhideProduct(Long productId) {
        int result = productRepository.unhideProduct(productId);

        if (result == 0){
            throw new EntityNotFoundException();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductEntity saveProduct(ProductEntity product) {

        if (product.getId() != null) {
            throw new InvalidIdentifierException("exception.id_is_not_null.message");
        }

        return productRepository.save(product);
    }
}
