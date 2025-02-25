package com.returdev.product.repositories;


import com.returdev.product.entities.BrandEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Repository interface for managing BrandEntity instances.
 * Provides methods for retrieving, updating, and managing brand data in the database.
 */
@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Long> {

    /**
     * Retrieves a paginated list of brands whose names contain the specified string.
     *
     * @param name the string to search for within brand names
     * @param includeInactive whether to include inactive brands in the search
     * @param pageable pagination information
     * @return a Page of BrandEntity containing matching brands
     */
    @Query("SELECT b FROM BrandEntity b WHERE (:includeInactive = true OR b.isActive = true) AND b.name LIKE CONCAT('%', :name, '%')")
    Page<BrandEntity> findByNameContaining(
            @Param("name") String name,
            @Param("includeInactive") boolean includeInactive,
            Pageable pageable
    );

    /**
     * Retrieves a paginated list of brands whose names start with the specified string.
     *
     * @param name the string to search for at the start of brand names
     * @param includeInactive whether to include inactive brands in the search
     * @param pageable pagination information
     * @return a Page of BrandEntity containing matching brands
     */
    @Query("SELECT b FROM BrandEntity b WHERE (:includeInactive = true OR b.isActive = true) AND b.name LIKE CONCAT(:name, '%')")
    Page<BrandEntity> findByNameStartingWith(
            @Param("name") String name,
            @Param("includeInactive") boolean includeInactive,
            Pageable pageable
    );

    /**
     * Retrieves a paginated list of all active brands.
     *
     * @param pageable pagination information
     * @return a Page of BrandEntity containing all active brands
     */
    @Query("SELECT b FROM BrandEntity b WHERE b.isActive = true")
    Page<BrandEntity> findAllActiveBrands(Pageable pageable);

    /**
     * Updates the name of a brand using a stored procedure.
     *
     * @param brandId the unique identifier of the brand
     * @param newName the new name for the brand
     * @return an Optional containing the updated BrandEntity with the new name
     */
    @Query(value = "CALL updateBrandName(:id, :name)", nativeQuery = true)
    Optional<BrandEntity> updateBrandName(@Param("id") Long brandId, @Param("name") String newName);

    /**
     * Updates the summary of a brand using a stored procedure.
     *
     * @param brandId the unique identifier of the brand
     * @param newSummary the new summary for the brand
     * @return an Optional containing the updated BrandEntity with the new summary
     */
    @Query(value = "CALL updateBrandSummary(:id, :summary)", nativeQuery = true)
    Optional<BrandEntity> updateBrandSummary(@Param("id") Long brandId, @Param("summary") String newSummary);


    /**
     * Activates a brand by setting its {@code isActive} status to true.
     *
     * @param brandId the ID of the brand to activate.
     * @return the number of rows affected by the update operation.
     */
    @Modifying
    @Transactional
    @Query("UPDATE BrandEntity b SET b.isActive = true WHERE b.id = :id")
    int activateBrand(@Param("id") Long brandId);

    /**
     * Deactivates a brand by setting its {@code isActive} status to false.
     *
     * @param brandId the ID of the brand to deactivate.
     * @return the number of rows affected by the update operation.
     */
    @Modifying
    @Transactional
    @Query("UPDATE BrandEntity b SET b.isActive = false WHERE b.id = :id")
    int deactivateBrand(@Param("id") Long brandId);
}


