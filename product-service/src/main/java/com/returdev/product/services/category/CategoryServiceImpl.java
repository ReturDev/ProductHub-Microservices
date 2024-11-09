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
import org.springframework.transaction.annotation.Transactional;
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
     * @throws EntityNotFoundException if no category exists with the provided {@code categoryId}.
     */
    @Transactional
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
     * @throws EntityNotFoundException if no category exists with the provided {@code categoryId}.
     */
    @Override
    public CategoryEntity updateCategoryName(
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
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no category exists with the provided {@code categoryId}.
     */
    @Override
    public CategoryEntity updateCategorySummary(Long categoryId, String newSummary) {
        return categoryRepository.updateCategorySummary(categoryId, newSummary).orElseThrow(() ->
                exceptionService.createEntityNotFoundException(categoryId)
        );
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
}

