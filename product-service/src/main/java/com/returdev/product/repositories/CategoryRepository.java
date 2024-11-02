package com.returdev.product.repositories;

import com.returdev.product.entities.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository interface for managing CategoryEntity instances.
 * Provides methods for retrieving, updating, and managing category data in the database.
 */
@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    /**
     * Retrieves a paginated list of categories whose names start with the specified string.
     *
     * @param name the string to search for at the beginning of category names
     * @param pageable pagination information
     * @return a Page of CategoryEntity containing matching categories
     */
    @Query("SELECT c FROM CategoryEntity c WHERE c.name LIKE CONCAT(:name, '%')")
    Page<CategoryEntity> findByNameStartingWith(@Param("name") String name, Pageable pageable);

    /**
     * Retrieves a paginated list of categories whose names contain the specified string.
     *
     * @param name the string to search for within category names
     * @param pageable pagination information
     * @return a Page of CategoryEntity containing matching categories
     */
    @Query("SELECT c FROM CategoryEntity c WHERE c.name LIKE CONCAT('%', :name, '%')")
    Page<CategoryEntity> findByNameContaining(@Param("name") String name, Pageable pageable);

    // Update methods using stored procedures

    /**
     * Updates the name of a category using a stored procedure.
     *
     * @param newName the new name for the category
     * @return the updated CategoryEntity
     */
    @Modifying
    @Transactional
    @Query(value = "CALL updateCategoryName(:name)", nativeQuery = true)
    CategoryEntity updateCategoryName(@Param("name") String newName);

    /**
     * Updates the summary of a category using a stored procedure.
     *
     * @param newSummary the new summary for the category
     * @return the updated CategoryEntity
     */
    @Modifying
    @Transactional
    @Query(value = "CALL updateCategorySummary(:summary)", nativeQuery = true)
    CategoryEntity updateCategorySummary(@Param("summary") String newSummary);
}
