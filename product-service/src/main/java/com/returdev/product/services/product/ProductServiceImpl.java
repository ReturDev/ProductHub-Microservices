package com.returdev.product.services.product;

import com.returdev.product.entities.DimensionsEntity;
import com.returdev.product.entities.ProductEntity;
import com.returdev.product.repositories.DimensionsRepository;
import com.returdev.product.repositories.ProductRepository;
import com.returdev.product.services.exception.ExceptionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;


/**
 * Implementation of the {@link ProductService} interface for managing products in the system.
 */
@Validated
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final DimensionsRepository dimensionsRepository;
    private final ExceptionService exceptionService;

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductEntity getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> exceptionService.createEntityNotFoundException(id)
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductEntity> getProductByCode(String code, boolean includeHidden, Pageable pageable) {
        return productRepository.findProductsByCode(code, includeHidden, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductEntity> getProductByBarCode(String barcode, boolean includeHidden, Pageable pageable) {
        return productRepository.findProductsByBarcode(barcode, includeHidden, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductEntity> getProductByNameContaining(String name, boolean includeHidden, Pageable pageable) {
        return productRepository.findProductsByNameContaining(name, includeHidden, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductEntity> getProductByNameStartingWith(String name, boolean includeHidden, Pageable pageable) {
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
        return includeHidden ? productRepository.findAll(pageable) : productRepository.findAllVisibleProducts(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductEntity> searchProducts(ProductEntity productEntity, boolean includeHidden, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
        if (includeHidden) {
            matcher.withIgnorePaths("isHidden");
        }
        return productRepository.findAll(Example.of(productEntity, matcher), pageable);
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if the {@code product} has a null ID.
     * @throws EntityNotFoundException  if no product is found with the provided ID.
     */
    @Transactional
    @Override
    public ProductEntity updateProduct(ProductEntity product) {
        if (product.getId() == null) {
            throw exceptionService.createIllegalArgumentException("exception.id_is_null.message");
        }

        Long productId = product.getId();

        if (!productRepository.existsById(productId)) {
            throw exceptionService.createEntityNotFoundException(productId);
        }

        return productRepository.save(product);
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no product is found with the provided {@code productId}.
     */
    @Override
    public ProductEntity updateProductName(Long productId, String newName) {
        return productRepository.updateProductName(productId, newName).orElseThrow(
                () -> exceptionService.createEntityNotFoundException(productId)
        );
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no product is found with the provided {@code productId}.
     */
    @Override
    public ProductEntity updateProductSummary(Long productId, String newSummary) {
        return productRepository.updateProductSummary(productId, newSummary).orElseThrow(
                () -> exceptionService.createEntityNotFoundException(productId)
        );
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no product is found with the provided {@code productId}.
     */
    @Override
    public ProductEntity updateProductCode(Long productId, String newCode) {
        return productRepository.updateProductCode(productId, newCode).orElseThrow(
                () -> exceptionService.createEntityNotFoundException(productId)
        );
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no product is found with the provided {@code productId}.
     */
    @Override
    public ProductEntity updateProductBarcode(Long productId, String barcode) {
        return productRepository.updateProductBarcode(productId, barcode).orElseThrow(
                () -> exceptionService.createEntityNotFoundException(productId)
        );
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no product is found with the provided {@code productId}.
     */
    @Override
    public ProductEntity updateProductModel(Long productId, Long modelId) {
        return productRepository.updateProductModel(productId, modelId).orElseThrow(
                () -> exceptionService.createEntityNotFoundException(productId)
        );
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if updating dimensions fails.
     */
    @Transactional
    @Override
    public ProductEntity updateProductDimensions(Long productId, DimensionsEntity dimensions) {
        Long savedDimensionId = dimensionsRepository.save(dimensions).getId();
        return productRepository.updateProductDimensions(productId, savedDimensionId).orElseThrow(
                () -> exceptionService.createEntityNotFoundException(productId)
        );
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if adding supplier fails.
     */
    @Override
    public void addProductSupplier(Long productId, Long newSupplierId) {
        int result = productRepository.addProductSupplier(productId, newSupplierId);
        if (result == 0) {
            throw exceptionService.createEntityNotFoundException();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if removing supplier fails.
     */
    @Override
    public void removeProductSupplier(Long productId, Long supplierId) {
        int result = productRepository.deleteProductSupplier(productId, supplierId);
        if (result == 0) {
            throw exceptionService.createEntityNotFoundException();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no product is found with the provided {@code productId}.
     */
    @Override
    public void hideProduct(Long productId) {
        int result = productRepository.hideProduct(productId);
        if (result == 0) {
            throw exceptionService.createEntityNotFoundException(productId);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no product is found with the provided {@code productId}.
     */
    @Override
    public void unhideProduct(Long productId) {
        int result = productRepository.unhideProduct(productId);
        if (result == 0) {
            throw exceptionService.createEntityNotFoundException(productId);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if the {@code product} already has a non-null ID.
     */
    @Override
    public ProductEntity saveProduct(ProductEntity product) {
        if (product.getId() != null) {
            throw exceptionService.createIllegalArgumentException("exception.id_is_not_null.message");
        }
        return productRepository.save(product);
    }
}

