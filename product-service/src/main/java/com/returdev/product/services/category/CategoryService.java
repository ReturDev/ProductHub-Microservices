package com.returdev.product.services.category;


import com.returdev.product.entities.CategoryEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing categories in the system.
 * Provides methods for retrieving categories by ID, name, and for retrieving all categories.
 */
public interface CategoryService {

    /**
     * Retrieves a category by its unique ID.
     *
     * @param id the unique identifier of the category
     * @return an Optional containing the found CategoryEntity, or empty if not found
     */
    CategoryEntity getCategoryById(@NotNull(message = "{validation.not_null.message}") Long id);

    /**
     * Retrieves a paginated list of categories containing the specified name.
     *
     * @param name the name to search for within category names
     * @param pageable pagination information
     * @return a Page of CategoryEntity containing categories whose names include the specified string
     */
    Page<CategoryEntity> getCategoryByNameContaining(
            @NotBlank(message = "{validation.not_blank.message}") String name,
            Pageable pageable
    );

    /**
     * Retrieves a paginated list of categories that start with the specified name.
     *
     * @param name the prefix of the category name to filter by
     * @param pageable pagination information
     * @return a Page of CategoryEntity containing categories that start with the specified string
     */
    Page<CategoryEntity> getCategoryByNameStartingWith(@NotBlank(message = "{validation.not_blank.message}") String name, Pageable pageable);

    /**
     * Retrieves a paginated list of all categories in the system.
     *
     * @param pageable pagination information
     * @return a Page of all CategoryEntity
     */
    Page<CategoryEntity> getAllCategories(Pageable pageable);

    /**
     * Updates a category with the provided entity.
     *
     * @param category the updated category entity
     * @return the updated CategoryEntity
     */
    CategoryEntity updateCategory(@Valid CategoryEntity category);

    /**
     * Updates the name of a category.
     *
     * @param categoryId the unique identifier of the category
     * @param newName the new name for the category
     * @return an Optional containing the updated CategoryEntity
     */
    CategoryEntity updateCategoryName(
            @NotNull(message = "{validation.not_null.message}")
            Long categoryId,
            @NotBlank(message = "{validation.not_blank.message}")
            @Size(min = 3, max = 50, message = "{validation.size.message}")
            String newName
    );

    /**
     * Updates the summary of a category.
     *
     * @param categoryId the unique identifier of the category
     * @param newSummary the new summary for the category
     * @return an Optional containing the updated CategoryEntity
     */
    CategoryEntity updateCategorySummary(
            @NotNull(message = "{validation.not_null.message}")
            Long categoryId,
            @NotNull(message = "{validation.not_null.message}")
            @Size(max = 150, message = "{validation.size.max.message}")
            String newSummary
    );

    /**
     * Saves a new category.
     *
     * @param category the category to save
     * @return the saved CategoryEntity
     */
    CategoryEntity saveCategory(@Valid CategoryEntity category);

}


