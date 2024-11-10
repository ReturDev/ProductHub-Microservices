package com.returdev.product.services.brand;


import com.returdev.product.entities.BrandEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service interface for managing brands in the system.
 * Provides methods for retrieving, updating, saving, deleting, and deactivating brand entities.
 */
public interface BrandService {

    /**
     * Retrieves a brand by its unique ID, with an option to include inactive brands.
     *
     * @param id the unique identifier of the brand
     * @return an Optional containing the found BrandEntity, or empty if not found
     */
    BrandEntity getBrandById(
            @NotNull(message = "{validation.not_null.message}") Long id
    );

    /**
     * Retrieves a paginated list of brands whose names contain the specified string.
     *
     * @param name the string to search within brand names
     * @param includeInactive whether to include inactive brands in the result
     * @param pageable pagination information
     * @return a Page of BrandEntity containing brands that match the search criteria
     */
    Page<BrandEntity> getBrandByNameContaining(
            @NotBlank(message = "{validation.not_blank.message}") String name,
            boolean includeInactive,
            Pageable pageable
    );

    /**
     * Retrieves a paginated list of brands that start with the specified name.
     *
     * @param name the prefix of the brand name to filter by
     * @param includeInactive whether to include inactive brands in the result
     * @param pageable pagination information
     * @return a Page of BrandEntity containing brands that start with the specified name
     */
    Page<BrandEntity> getBrandByNameStartingWith(
            @NotBlank(message = "{validation.not_blank.message}") String name,
            boolean includeInactive,
            Pageable pageable
    );

    /**
     * Retrieves a paginated list of all brands.
     *
     * @param pageable pagination information
     * @param includeInactive whether to include inactive brands in the result
     * @return a Page of BrandEntity containing all brands
     */
    Page<BrandEntity> getAllBrands(
            Pageable pageable,
            boolean includeInactive
    );

    /**
     * Updates the details of an existing brand.
     *
     * @param brand the BrandEntity containing updated details
     * @return the updated BrandEntity
     */
    @Transactional
    BrandEntity updateBrand(@Valid BrandEntity brand);

    /**
     * Updates an existing {@link BrandEntity} with a new name and summary.
     *
     * @param brandId the ID of the brand to update; must not be null
     * @param newName the new name for the brand; must not be blank and must have a length between 3 and 50 characters
     * @param newSummary the new summary for the brand; must not be null and must not exceed 150 characters in length
     * @return the updated {@link BrandEntity} with the modified name and summary
     */
    @Transactional
    BrandEntity updateBrand(
            @NotNull(message = "{validation.not_null.message}")
            Long brandId,
            @NotBlank(message = "{validation.not_blank.message}")
            @Size(min = 3, max = 50, message = "{validation.size.message}")
            String newName,
            @NotNull(message = "{validation.not_null.message}")
            @Size(max = 150, message = "{validation.size.max.message}")
            String newSummary
    );

    /**
     * Saves a new brand entity.
     *
     * @param brand the brand entity to save
     * @return the saved BrandEntity
     */
    BrandEntity saveBrand(@Valid BrandEntity brand);

    /**
     * Activates a brand by its ID.
     *
     * @param brandId the unique identifier of the brand
     */
    void activateBrand(@NotNull(message = "{validation.not_null.message}") Long brandId);

    /**
     * Deactivates a brand, making it inactive in the system.
     *
     * @param brandId the unique identifier of the brand to deactivate
     */
    void deactivateBrand(@NotNull(message = "{validation.not_null.message}") Long brandId);

}


