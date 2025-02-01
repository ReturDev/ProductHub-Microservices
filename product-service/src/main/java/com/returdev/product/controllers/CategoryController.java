package com.returdev.product.controllers;

import com.returdev.product.dtos.request.CategoryRequestDTO;
import com.returdev.product.dtos.request.PaginationRequestDTO;
import com.returdev.product.dtos.response.CategoryResponseDTO;
import com.returdev.product.dtos.response.wrappers.ContentResponseWrapper;
import com.returdev.product.dtos.response.wrappers.PaginationResponseWrapper;
import com.returdev.product.entities.CategoryEntity;
import com.returdev.product.mappers.EntityDtoMapper;
import com.returdev.product.services.category.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing category-related endpoints.
 * Handles CRUD operations and retrieval of category data, and maps category entity objects
 * to their respective DTO representations.
 */
@RestController
@RequestMapping("/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final EntityDtoMapper entityDtoMapper;

    /**
     * Retrieves a specific category by its ID.
     *
     * @param categoryId the ID of the category to retrieve
     * @return a {@link ContentResponseWrapper} containing the {@link CategoryResponseDTO} of the retrieved category
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContentResponseWrapper<CategoryResponseDTO> getCategoryById(@PathVariable("id") Long categoryId) {
        return entityDtoMapper.categoryEntityToContentResponse(
                categoryService.getCategoryById(categoryId)
        );
    }

    /**
     * Retrieves a paginated list of all categories.
     *
     * @param pagination the pagination parameters for the request
     * @return a {@link PaginationResponseWrapper} containing a paginated list of {@link CategoryResponseDTO} objects
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public PaginationResponseWrapper<CategoryResponseDTO> getCategories(
            @Valid PaginationRequestDTO pagination
    ) {
        return entityDtoMapper.categoryEntityPageToPaginationResponse(
                categoryService.getAllCategories(
                        entityDtoMapper.paginationRequestToPageable(pagination)
                )
        );
    }

    /**
     * Searches for categories by name with optional wildcard support.
     *
     * @param name the search string, with optional '*' wildcard at the start
     * @param pagination the pagination parameters for the request
     * @return a {@link PaginationResponseWrapper} containing a paginated list of {@link CategoryResponseDTO} objects
     */
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public PaginationResponseWrapper<CategoryResponseDTO> getCategoriesByName(
            @RequestParam() String name,
            @Valid PaginationRequestDTO pagination
    ) {
        Pageable pageable = entityDtoMapper.paginationRequestToPageable(pagination);
        Page<CategoryEntity> categoryPage;

        if (name.startsWith("*")) {
            categoryPage = categoryService.getCategoryByNameContaining(name.replace("*", ""), pageable);
        } else {
            categoryPage = categoryService.getCategoryByNameStartingWith(name, pageable);
        }

        return entityDtoMapper.categoryEntityPageToPaginationResponse(categoryPage);
    }

    /**
     * Updates an existing category with new data.
     *
     * @param categoryRequestDTO the DTO containing the updated category data
     * @return a {@link ContentResponseWrapper} containing the updated {@link CategoryResponseDTO}
     */
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ContentResponseWrapper<CategoryResponseDTO> updateCategory(
            @RequestBody @Valid CategoryRequestDTO categoryRequestDTO
    ) {
        return entityDtoMapper.categoryEntityToContentResponse(
                categoryService.updateCategory(
                        entityDtoMapper.categoryRequestToEntity(categoryRequestDTO)
                )
        );
    }

    /**
     * Partially updates specific properties of an existing category.
     *
     * @param categoryRequestDTO the DTO containing the properties to update
     * @return a {@link ContentResponseWrapper} containing the updated {@link CategoryResponseDTO}
     */
    @PatchMapping()
    @ResponseStatus(HttpStatus.OK)
    public ContentResponseWrapper<CategoryResponseDTO> updateCategoryProperty(
            @RequestBody CategoryRequestDTO categoryRequestDTO
    ) {
        return entityDtoMapper.categoryEntityToContentResponse(
                categoryService.updateCategory(
                        categoryRequestDTO.id(),
                        categoryRequestDTO.name(),
                        categoryRequestDTO.summary()
                )
        );
    }

    /**
     * Creates a new category with the provided data.
     *
     * @param categoryRequestDTO the DTO containing the data for the new category
     * @return a {@link ContentResponseWrapper} containing the created {@link CategoryResponseDTO}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContentResponseWrapper<CategoryResponseDTO> saveCategory(
            @RequestBody @Valid CategoryRequestDTO categoryRequestDTO
    ) {
        return entityDtoMapper.categoryEntityToContentResponse(
                categoryService.saveCategory(
                        entityDtoMapper.categoryRequestToEntity(categoryRequestDTO)
                )
        );
    }

}

