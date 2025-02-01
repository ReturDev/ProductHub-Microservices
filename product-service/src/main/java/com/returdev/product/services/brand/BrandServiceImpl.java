package com.returdev.product.services.brand;

import com.returdev.product.entities.BrandEntity;
import com.returdev.product.repositories.BrandRepository;
import com.returdev.product.services.exception.ExceptionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


/**
 * Implementation of the {@link BrandService} interface for managing brands in the system.
 */
@Validated
@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final ExceptionService exceptionService;

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no brand is found with the given {@code id}.
     */
    @Override
    public BrandEntity getBrandById(
            Long id
    ) {
        return brandRepository.findById(id).orElseThrow(() -> exceptionService.createEntityNotFoundException(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<BrandEntity> getBrandByNameContaining(
            String name,
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
            String name,
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
     * @throws IllegalArgumentException if the {@code brand.id} is {@code null} while updating an existing brand.
     * @throws EntityNotFoundException if the {@code brand} does not exist in the database.
     */
    @Override
    public BrandEntity updateBrand(BrandEntity brand) {
        if (brand.getId() == null) {
            throw exceptionService.createIllegalArgumentException("exception.id_is_null.message");
        }

        Long brandId = brand.getId();

        if (!brandRepository.existsById(brandId)) {
            throw exceptionService.createEntityNotFoundException(brandId);
        }
        return brandRepository.save(brand);
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if neither the new name nor the new summary is provided.
     * @throws EntityNotFoundException if the {@code brand} does not exist in the database.
     **/
    @Override
    public BrandEntity updateBrand(Long brandId, String newName, String newSummary) {

        if (!brandRepository.existsById(brandId)) {
            throw exceptionService.createEntityNotFoundException(brandId);
        }

        BrandEntity brandResponse = null;

        if (newName != null) {
            brandResponse = updateBrandName(brandId, newName);
        }

        if (newSummary != null) {
            brandResponse = updateBrandSummary(brandId, newSummary);
        }

        if (brandResponse == null){
            throw exceptionService.createIllegalArgumentException("exception.null_update_values.message");
        }

        return brandResponse;
    }


    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if the {@code brand} has a non-null ID, as it should be null for a new brand.
     */
    @Override
    public BrandEntity saveBrand(BrandEntity brand) {
        if (brand.getId() != null) {
            throw exceptionService.createIllegalArgumentException("exception.id_is_not_null.message");
        }
        return brandRepository.save(brand);
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no existing brand is found with the provided {@code brandId}.
     */
    @Override
    public void activateBrand(Long brandId) {
        int result = brandRepository.activateBrand(brandId);
        if (result == 0) {
            throw exceptionService.createEntityNotFoundException(brandId);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no existing brand is found with the provided {@code brandId}.
     */
    @Override
    public void deactivateBrand(Long brandId) {
        int result = brandRepository.deactivateBrand(brandId);
        if (result == 0) {
            throw exceptionService.createEntityNotFoundException(brandId);
        }
    }

    /**
     * Updates the name of a brand.
     *
     * @param brandId the unique identifier of the brand
     * @param newName the new name for the brand
     * @return an Optional containing the updated BrandEntity
     * @throws EntityNotFoundException if no existing brand is found with the provided {@code brandId}.
     */
    private BrandEntity updateBrandName(Long brandId, String newName) {
        return brandRepository.updateBrandName(brandId, newName).orElseThrow(() ->
                exceptionService.createEntityNotFoundException(brandId)
        );
    }

    /**
     * Updates the summary of a brand.
     *
     * @param brandId    the unique identifier of the brand
     * @param newSummary the new summary for the brand
     * @return an Optional containing the updated BrandEntity
     * @throws EntityNotFoundException if no existing brand is found with the provided {@code brandId}.
     */
    private BrandEntity updateBrandSummary(Long brandId, String newSummary) {
        return brandRepository.updateBrandSummary(brandId, newSummary).orElseThrow(() ->
                exceptionService.createEntityNotFoundException(brandId)
        );
    }

}
