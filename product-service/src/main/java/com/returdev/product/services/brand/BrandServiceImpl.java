package com.returdev.product.services.brand;

import com.returdev.product.entities.BrandEntity;
import com.returdev.product.exceptions.InvalidIdentifierException;
import com.returdev.product.repositories.BrandRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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
     */
    @Override
    public Optional<BrandEntity> getBrandById(
            @NotNull(message = "${validation.not_null.message}") Long id
    ) {
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
     */
    @Override
    public BrandEntity updateBrand(@Valid BrandEntity brand) {

        if (brand.getId() == null) {
            throw new InvalidIdentifierException("exception.id_is_null.message");
        }

        if (!brandRepository.existsById(brand.getId())) {
            throw new InvalidIdentifierException("exception.not_exists_by_id.message");
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
     */
    @Override
    public void activateBrand(
            @NotNull(message = "${validation.not_null.message}") Long brandId
    ) {
        brandRepository.activateBrand(brandId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deactivateBrand(
            @NotNull(message = "${validation.not_null.message}") Long brandId
    ) {
        brandRepository.deactivateBrand(brandId);
    }

}
