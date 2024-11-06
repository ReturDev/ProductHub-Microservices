package com.returdev.product.services.brand;

import com.returdev.product.entities.BrandEntity;
import com.returdev.product.exceptions.InvalidIdentifierException;
import com.returdev.product.repositories.BrandRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;


/**
 * Implementation of the {@link BrandService} interface for managing brands in the system.
 */
@Validated
@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    /**
     * {@inheritDoc}
     *
     * @throws InvalidIdentifierException if the provided {@code id} is {@code null}.
     */
    @Override
    public Optional<BrandEntity> getBrandById(
            @NotNull(message = "${validation.not_null.message}") Long id
    ) {
        if (id == null) {
            throw new InvalidIdentifierException("exception.id_is_null.message");
        }
        return brandRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<BrandEntity> getBrandByNameContaining(
            @NotBlank(message = "${validation.not_blank.message}") String name,
            boolean includeInactive,
            Pageable pageable
    ) {
        return brandRepository.findByNameContaining(name, includeInactive, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<BrandEntity> getBrandByNameStartingWith(
            @NotBlank(message = "${validation.not_blank.message}") String name,
            boolean includeInactive,
            Pageable pageable
    ) {
        return brandRepository.findByNameStartingWith(name, includeInactive, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<BrandEntity> getAllBrands(Pageable pageable, boolean includeInactive) {
        if (includeInactive) {
            return brandRepository.findAll(pageable);
        } else {
            return brandRepository.findAllActiveBrands(pageable);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws InvalidIdentifierException if the {@code brand} has a {@code null} ID.
     * @throws EntityNotFoundException if no existing brand is found with the provided ID.
     */
    @Transactional
    @Override
    public BrandEntity updateBrand(@Valid BrandEntity brand) {
        if (brand.getId() == null) {
            throw new InvalidIdentifierException("exception.id_is_null.message");
        }
        if (!brandRepository.existsById(brand.getId())) {
            throw new EntityNotFoundException();
        }
        return brandRepository.save(brand);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<BrandEntity> updateBrandName(
            @NotNull(message = "${validation.not_null.message}") Long brandId,
            @NotBlank(message = "${validation.not_blank.message}")
            @Size(min = 3, max = 50, message = "${validation.size.message}")
            String newName
    ) {
        return brandRepository.updateBrandName(brandId, newName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<BrandEntity> updateBrandSummary(
            @NotNull(message = "${validation.not_null.message}") Long brandId,
            @NotNull(message = "${validation.not_null.message}")
            @Size(max = 150, message = "${validation.size.max.message}")
            String newSummary
    ) {
        return brandRepository.updateBrandSummary(brandId, newSummary);
    }

    /**
     * {@inheritDoc}
     *
     * @throws InvalidIdentifierException if the {@code brand} has a non-null ID, as it should be null for a new brand.
     */
    @Override
    public BrandEntity saveBrand(@Valid BrandEntity brand) {
        if (brand.getId() != null) {
            throw new InvalidIdentifierException("exception.id_is_not_null.message");
        }
        return brandRepository.save(brand);
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no existing brand is found with the provided {@code brandId}.
     */
    @Override
    public void activateBrand(
            @NotNull(message = "${validation.not_null.message}") Long brandId
    ) {
        int result = brandRepository.activateBrand(brandId);
        if (result == 0) {
            throw new EntityNotFoundException();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no existing brand is found with the provided {@code brandId}.
     */
    @Override
    public void deactivateBrand(
            @NotNull(message = "${validation.not_null.message}") Long brandId
    ) {
        int result = brandRepository.deactivateBrand(brandId);
        if (result == 0) {
            throw new EntityNotFoundException();
        }
    }
}
