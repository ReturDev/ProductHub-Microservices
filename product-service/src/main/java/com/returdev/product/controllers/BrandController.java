package com.returdev.product.controllers;

import com.returdev.product.dtos.request.BrandRequestDTO;
import com.returdev.product.dtos.request.PaginationRequestDTO;
import com.returdev.product.dtos.response.BrandResponseDTO;
import com.returdev.product.dtos.response.wrappers.ContentResponseWrapper;
import com.returdev.product.dtos.response.wrappers.PaginationResponseWrapper;
import com.returdev.product.entities.BrandEntity;
import com.returdev.product.mappers.EntityDtoMapper;
import com.returdev.product.services.brand.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing brand entities.
 * Provides endpoints for retrieving, creating, updating, activating, and deactivating brands.
 */
@RestController
@RequestMapping("/v1/brand")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;
    private final EntityDtoMapper entityDtoMapper;

    /**
     * Retrieves a brand by its unique identifier.
     *
     * @param brandId the ID of the brand to retrieve
     * @return a wrapper containing the {@link BrandResponseDTO} of the requested brand
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContentResponseWrapper<BrandResponseDTO> getBrandById(
            @PathVariable("id") Long brandId
    ) {
        return entityDtoMapper.brandEntityToContentResponse(
                brandService.getBrandById(brandId)
        );
    }

    /**
     * Retrieves a paginated list of all brands, with an option to include inactive brands.
     *
     * @param pagination the pagination and sorting parameters
     * @param showInactives flag indicating whether inactive brands should be included in the response
     * @return a wrapper containing a paginated list of {@link BrandResponseDTO} objects
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public PaginationResponseWrapper<BrandResponseDTO> getBrands(
            @Valid PaginationRequestDTO pagination,
            @RequestParam(defaultValue = "false", required = false) boolean showInactives
    ){
        return entityDtoMapper.brandEntityPageToPaginationResponse(
                brandService.getAllBrands(
                        entityDtoMapper.paginationRequestToPageable(pagination),
                        showInactives
                )
        );
    }

    /**
     * Retrieves a paginated list of brands filtered by name. Supports wildcard search.
     *
     * @param name the name to search for; supports prefix search or full wildcard search if prefixed with '*'
     * @param pagination the pagination and sorting parameters
     * @param showInactives flag indicating whether inactive brands should be included in the response
     * @return a wrapper containing a paginated list of {@link BrandResponseDTO} objects matching the search criteria
     */
    @GetMapping("/name")
    @ResponseStatus(HttpStatus.OK)
    public PaginationResponseWrapper<BrandResponseDTO> getBrandsByName(
            @RequestParam() String name,
            @Valid PaginationRequestDTO pagination,
            @RequestParam(defaultValue = "false", required = false) boolean showInactives
    ) {

        Pageable pageable = entityDtoMapper.paginationRequestToPageable(pagination);

        Page<BrandEntity> brandPage;

        if (name.startsWith("*")){
            brandPage = brandService.getBrandByNameContaining(name, showInactives, pageable);
        } else {
            brandPage = brandService.getBrandByNameStartingWith(name, showInactives, pageable);
        }

        return entityDtoMapper.brandEntityPageToPaginationResponse(brandPage);

    }

    /**
     * Updates the details of an existing brand.
     *
     * @param brandRequestDTO the new details of the brand
     * @return a wrapper containing the updated {@link BrandResponseDTO}
     */
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ContentResponseWrapper<BrandResponseDTO> updateBrand(
            @RequestBody @Valid BrandRequestDTO brandRequestDTO
    ){
        return entityDtoMapper.brandEntityToContentResponse(
                brandService.updateBrand(
                        entityDtoMapper.brandRequestToEntity(brandRequestDTO)
                )
        );

    }

    /**
     * Partially updates properties of an existing brand, specifically its name and summary.
     *
     * @param brandRequestDTO a {@link BrandRequestDTO} containing the properties to update
     * @return a wrapper containing the updated {@link BrandResponseDTO}
     */
    @PatchMapping()
    @ResponseStatus(HttpStatus.OK)
    public ContentResponseWrapper<BrandResponseDTO> updateBrandProperty(
            @RequestBody BrandRequestDTO brandRequestDTO
    ) {
        return entityDtoMapper.brandEntityToContentResponse(
                brandService.updateBrand(brandRequestDTO.id(), brandRequestDTO.name(), brandRequestDTO.summary())
        );
    }

    /**
     * Activates a brand, making it available for use.
     *
     * @param brandId the ID of the brand to activate
     */
    @PatchMapping("/{id}/activate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activateBrand(
            @PathVariable("id") Long brandId
    ) {
        brandService.activateBrand(brandId);
    }

    /**
     * Deactivates a brand, making it unavailable for use.
     *
     * @param brandId the ID of the brand to deactivate
     */
    @PatchMapping("/{id}/deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateBrand(
            @PathVariable("id") Long brandId
    ) {
        brandService.deactivateBrand(brandId);
    }

    /**
     * Creates a new brand with the specified details.
     *
     * @param brandRequestDTO a {@link BrandRequestDTO} containing the details of the new brand
     * @return a wrapper containing the newly created {@link BrandResponseDTO}
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public ContentResponseWrapper<BrandResponseDTO> saveBrand(
            @RequestBody @Valid BrandRequestDTO brandRequestDTO
    ) {
        return entityDtoMapper.brandEntityToContentResponse(
                brandService.saveBrand(
                        entityDtoMapper.brandRequestToEntity(brandRequestDTO)
                )
        );
    }

}

