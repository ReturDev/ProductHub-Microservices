package com.returdev.product.services.category;

import com.returdev.product.entities.CategoryEntity;
import com.returdev.product.repositories.CategoryRepository;
import com.returdev.product.services.exception.ExceptionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * Implementation of the {@link CategoryService} interface for managing categories in the system.
 */
@Validated
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ExceptionService exceptionService;

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no category is found with the provided {@code id}.
     */
    @Override
    public CategoryEntity getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> exceptionService.createEntityNotFoundException(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<CategoryEntity> getCategoryByNameContaining(String name, Pageable pageable) {
        return categoryRepository.findByNameContaining(name, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<CategoryEntity> getCategoryByNameStartingWith(String name, Pageable pageable) {
        return categoryRepository.findByNameStartingWith(name, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<CategoryEntity> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if the {@code category} has a null ID, as it should be present for updating.
     * @throws EntityNotFoundException  if no category exists with the provided {@code categoryId}.
     */
    @Override
    public CategoryEntity updateCategory(CategoryEntity category) {
        if (category.getId() == null) {
            throw exceptionService.createIllegalArgumentException("exception.id_is_null.message");
        }

        Long categoryId = category.getId();

        if (!categoryRepository.existsById(categoryId)) {
            throw exceptionService.createEntityNotFoundException(categoryId);
        }
        return categoryRepository.save(category);
    }


    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if neither the new name nor the new summary is provided
     * @throws EntityNotFoundException if the contact to update does not exist.
     */
    @Override
    public CategoryEntity updateCategory(Long categoryId, String newName, String newSummary) {

        if (!categoryRepository.existsById(categoryId)) {
            throw exceptionService.createEntityNotFoundException(categoryId);
        }

        CategoryEntity categoryResponse = null;

        if (newName != null) {
            categoryResponse = updateCategoryName(categoryId, newName);
        }

        if (newSummary != null) {
            categoryResponse = updateCategorySummary(categoryId, newSummary);
        }

        if (categoryResponse == null) {
            throw exceptionService.createIllegalArgumentException("exception.null_update_values.message");
        }

        return categoryResponse;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if the {@code category} has a non-null ID, as it should be null for a new category.
     */
    @Override
    public CategoryEntity saveCategory(CategoryEntity category) {
        if (category.getId() != null) {
            throw exceptionService.createIllegalArgumentException("exception.id_is_not_null.message");
        }
        return categoryRepository.save(category);
    }

    /**
     * Updates the name of the category identified by the given category ID.
     * If the category does not exist, an exception is thrown.
     *
     * @param categoryId the ID of the category to update
     * @param newName the new name for the category
     * @return the updated {@link CategoryEntity} with the new name
     * @throws EntityNotFoundException if no category with the specified ID is found
     */
    private CategoryEntity updateCategoryName(
            Long categoryId,
            @NotBlank(message = "{validation.not_blank.message}")
            @Size(min = 3, max = 50, message = "{validation.size.message}")
            String newName
    ) {
        return categoryRepository.updateCategoryName(categoryId, newName).orElseThrow(() ->
                exceptionService.createEntityNotFoundException(categoryId)
        );
    }

    /**
     * Updates the summary of the category identified by the given category ID.
     * If the category does not exist, an exception is thrown.
     *
     * @param categoryId the ID of the category to update
     * @param newSummary the new summary for the category
     * @return the updated {@link CategoryEntity} with the new summary
     * @throws EntityNotFoundException if no category with the specified ID is found
     */
    private CategoryEntity updateCategorySummary(Long categoryId, String newSummary) {
        return categoryRepository.updateCategorySummary(categoryId, newSummary).orElseThrow(() ->
                exceptionService.createEntityNotFoundException(categoryId)
        );
    }


}

