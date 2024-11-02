package com.returdev.product.services.brand;


import com.returdev.product.entities.BrandEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service interface for managing brands in the system.
 * Provides methods for retrieving, updating, saving, deleting, and deactivating brand entities.
 */
public interface BrandService {

    // GET

    /**
     * Retrieves a brand by its unique ID, with an option to include inactive brands.
     *
     * @param id the unique identifier of the brand
     * @return an Optional containing the found BrandEntity, or empty if not found
     */
    Optional<BrandEntity> getBrandById(Long id);


    /**
     * Retrieves a paginated list of brands whose names contain the specified string.
     *
     * @param name the string to search within brand names
     * @param pageable pagination information
     * @param includeInactive whether to include inactive brands in the result
     * @return a Page of BrandEntity containing brands that match the search criteria
     */
    Page<BrandEntity> getBrandContainingName(String name, Pageable pageable, boolean includeInactive);

    /**
     * Retrieves a paginated list of all brands.
     *
     * @param pageable pagination information
     * @param includeInactive whether to include inactive brands in the result
     * @return a Page of BrandEntity containing all brands
     */
    Page<BrandEntity> getAllBrands(Pageable pageable, boolean includeInactive);

    // UPDATE

    /**
     * Updates the name of a brand.
     *
     * @param brandId the unique identifier of the brand
     * @param newName the new name for the brand
     * @return the updated BrandEntity
     */
    BrandEntity updateBrandName(Long brandId, String newName);

    /**
     * Updates the summary of a brand.
     *
     * @param brandId the unique identifier of the brand
     * @param newSummary the new summary for the brand
     * @return the updated BrandEntity
     */
    BrandEntity updateBrandSummary(Long brandId, String newSummary);

    // SAVE

    /**
     * Saves a new brand entity.
     *
     * @param brand the brand entity to save
     * @return the saved BrandEntity
     */
    BrandEntity saveBrand(BrandEntity brand);

    /**
     * Activates a brand by its ID.
     *
     * @param brandId the unique identifier of the brand
     * @return the activated BrandEntity
     */
    BrandEntity activateBrand(Long brandId);

    // DELETE / DEACTIVATE

    /**
     * Deletes a brand by its ID. This operation should be used with caution.
     *
     * @param id the unique identifier of the brand to delete
     */
    void deleteBrand(Long id);

    /**
     * Deactivates a brand, making it inactive in the system.
     *
     * @param id the unique identifier of the brand to deactivate
     */
    void deactivateBrand(Long id);
}


