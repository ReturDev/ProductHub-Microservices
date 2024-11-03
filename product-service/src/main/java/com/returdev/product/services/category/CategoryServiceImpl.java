package com.returdev.product.services.category;

import com.returdev.product.entities.CategoryEntity;
import com.returdev.product.repositories.CategoryRepository;
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
 * Implementation of the CategoryService interface for managing categories in the system.
 */
@Validated
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CategoryEntity> getCategoryById(
            @NotNull(message = "${validation.not_null.message}") Long id
    ) {
        return categoryRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<CategoryEntity> getCategoryByNameContaining(
            @NotBlank(message = "${validation.not_blank.message}") String name,
            Pageable pageable
    ) {
        return categoryRepository.findByNameContaining(name, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<CategoryEntity> getCategoryByNameStartingWith(
            @NotBlank(message = "${validation.not_blank.message}") String name,
            Pageable pageable
    ) {
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
     */
    @Override
    public CategoryEntity updateCategory(@Valid CategoryEntity category) {

        if (category.getId() == null) {
            throw new IllegalArgumentException("validation.id_is_null.message");
        }

        return categoryRepository.save(category);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CategoryEntity updateCategoryName(
            @NotNull(message = "${validation.not_null.message}") Long categoryId,
            @NotBlank(message = "${validation.not_blank.message}")
            @Size(min = 3, max = 50, message = "${validation.size.message}")
            String newName
    ) {
        return categoryRepository.updateCategoryName(categoryId, newName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CategoryEntity updateCategorySummary(
            @NotNull(message = "${validation.not_null.message}") Long categoryId,
            @NotNull(message = "${validation.not_null.message}")
            @Size(max = 150, message = "${validation.size.max.message}")
            String newSummary
    ) {
        return categoryRepository.updateCategorySummary(categoryId, newSummary);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CategoryEntity saveCategory(CategoryEntity category) {

        if (category.getId() != null) {
            throw new IllegalArgumentException("validation.id_is_not_null.message");
        }

        return categoryRepository.save(category);

    }

}
