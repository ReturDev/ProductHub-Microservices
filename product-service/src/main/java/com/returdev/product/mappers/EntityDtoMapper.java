package com.returdev.product.mappers;


import com.returdev.product.dtos.request.*;
import com.returdev.product.dtos.response.*;
import com.returdev.product.dtos.response.wrappers.ContentResponseWrapper;
import com.returdev.product.dtos.response.wrappers.PaginationResponseWrapper;
import com.returdev.product.entities.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface for mapping entities to DTOs and wrapping them into response formats,
 * as well as converting DTOs back into entities for CRUD operations.
 * This interface facilitates the transformation between entity and DTO layers in a
 * standardized manner, promoting consistency across API responses.
 *
 * <p>Each mapping method focuses on a specific entity and its corresponding DTO, ensuring
 * seamless conversion in both directions where applicable.</p>
 */
public interface EntityDtoMapper {

    /**
     * Maps a {@link BrandEntity} to a {@link ContentResponseWrapper} containing a {@link BrandResponseDTO}.
     *
     * @param brandEntity the brand entity to map
     * @return a wrapper containing the mapped brand DTO
     */
    ContentResponseWrapper<BrandResponseDTO> brandEntityToContentResponse(BrandEntity brandEntity);

    /**
     * Maps a {@link Page} of {@link BrandEntity} to a {@link PaginationResponseWrapper} of {@link BrandResponseDTO}.
     *
     * @param brandEntityPage the page of brand entities to map
     * @return a pagination wrapper containing the mapped brand DTOs
     */
    PaginationResponseWrapper<BrandResponseDTO> brandEntityPageToPaginationResponse(Page<BrandEntity> brandEntityPage);

    /**
     * Maps a {@link BrandRequestDTO} to a {@link BrandEntity}.
     *
     * @param brandRequestDTO the brand DTO to map
     * @return the mapped brand entity
     */
    BrandEntity brandRequestToEntity(BrandRequestDTO brandRequestDTO);

    /**
     * Maps a {@link CategoryEntity} to a {@link ContentResponseWrapper} containing a {@link CategoryResponseDTO}.
     *
     * @param categoryEntity the category entity to map
     * @return a wrapper containing the mapped category DTO
     */
    ContentResponseWrapper<CategoryResponseDTO> categoryEntityToContentResponse(CategoryEntity categoryEntity);

    /**
     * Maps a {@link Page} of {@link CategoryEntity} to a {@link PaginationResponseWrapper} of {@link CategoryResponseDTO}.
     *
     * @param categoryEntityPage the page of category entities to map
     * @return a pagination wrapper containing the mapped category DTOs
     */
    PaginationResponseWrapper<CategoryResponseDTO> categoryEntityPageToPaginationResponse(Page<CategoryEntity> categoryEntityPage);

    /**
     * Maps a {@link CategoryRequestDTO} to a {@link CategoryEntity}.
     *
     * @param categoryRequestDTO the category DTO to map
     * @return the mapped category entity
     */
    CategoryEntity categoryRequestToEntity(CategoryRequestDTO categoryRequestDTO);

    /**
     * Maps a {@link ContactInfoEntity} to a {@link ContentResponseWrapper} containing a {@link SupplierResponseDTO.ContactInfoResponseDTO}.
     *
     * @param contactInfoEntity the contact info entity to map
     * @return a wrapper containing the mapped contact info DTO
     */
    ContentResponseWrapper<SupplierResponseDTO.ContactInfoResponseDTO> contactInfoEntityToContentResponse(ContactInfoEntity contactInfoEntity);

    /**
     * Maps a {@link ContactInfoRequestDTO} to a {@link ContactInfoEntity}.
     *
     * @param contactInfoRequestDTO the contact info DTO to map
     * @return the mapped contact info entity
     */
    ContactInfoEntity contactInfoRequestToEntity(ContactInfoRequestDTO contactInfoRequestDTO);

    /**
     * Maps a {@link ModelEntity} to a {@link ContentResponseWrapper} containing a {@link ModelResponseDTO}.
     *
     * @param modelEntity the model entity to map
     * @return a wrapper containing the mapped model DTO
     */
    ContentResponseWrapper<ModelResponseDTO> modelEntityToContentResponse(ModelEntity modelEntity);

    /**
     * Maps a {@link Page} of {@link ModelEntity} to a {@link PaginationResponseWrapper} of {@link ModelResponseDTO}.
     *
     * @param modelEntityPage the page of model entities to map
     * @return a pagination wrapper containing the mapped model DTOs
     */
    PaginationResponseWrapper<ModelResponseDTO> modelEntityPageToPaginationResponse(Page<ModelEntity> modelEntityPage);

    /**
     * Maps a {@link ModelRequestDTO} to a {@link ModelEntity}.
     *
     * @param modelRequestDTO the model DTO to map
     * @return the mapped model entity
     */
    ModelEntity modelRequestToEntity(ModelRequestDTO modelRequestDTO);

    /**
     * Maps a {@link ProductEntity} to a {@link ContentResponseWrapper} containing a {@link ProductResponseDTO}.
     *
     * @param productEntity the product entity to map
     * @return a wrapper containing the mapped product DTO
     */
    ContentResponseWrapper<ProductResponseDTO> productEntityToContentResponse(ProductEntity productEntity);

    /**
     * Maps a {@link Page} of {@link ProductEntity} to a {@link PaginationResponseWrapper} of {@link ProductResponseDTO}.
     *
     * @param productEntityPage the page of product entities to map
     * @return a pagination wrapper containing the mapped product DTOs
     */
    PaginationResponseWrapper<ProductResponseDTO> productEntityPageToPaginationResponse(Page<ProductEntity> productEntityPage);

    /**
     * Maps a {@link ProductRequestDTO} to a {@link ProductEntity}.
     *
     * @param productRequestDTO the product DTO to map
     * @return the mapped product entity
     */
    ProductEntity productRequestToEntity(ProductRequestDTO productRequestDTO);

    /**
     * Maps a {@link SupplierEntity} to a {@link ContentResponseWrapper} containing a {@link SupplierResponseDTO}.
     *
     * @param supplierEntity the supplier entity to map
     * @return a wrapper containing the mapped supplier DTO
     */
    ContentResponseWrapper<SupplierResponseDTO> supplierEntityToContentResponse(SupplierEntity supplierEntity);

    /**
     * Maps a {@link Page} of {@link SupplierEntity} to a {@link PaginationResponseWrapper} of {@link SupplierResponseDTO}.
     *
     * @param supplierEntityPage the page of supplier entities to map
     * @return a pagination wrapper containing the mapped supplier DTOs
     */
    PaginationResponseWrapper<SupplierResponseDTO> supplierEntityPageToPaginationResponse(Page<SupplierEntity> supplierEntityPage);

    /**
     * Maps a {@link SupplierRequestDTO} to a {@link SupplierEntity}.
     *
     * @param supplierRequestDTO the supplier DTO to map
     * @return the mapped supplier entity
     */
    SupplierEntity supplierRequestToEntity(SupplierRequestDTO supplierRequestDTO);

    /**
     * Maps a {@link ProductRequestDTO.DimensionsRequestDTO} to a {@link DimensionsEntity}.
     *
     * @param dimensionsRequestDTO the DTO containing the product dimensions to be converted (must not be null)
     * @return return the mapped dimension
     */
    DimensionsEntity dimensionRequestToEntity(ProductRequestDTO.DimensionsRequestDTO dimensionsRequestDTO);

    /**
     * Maps a {@link PaginationRequestDTO} to a {@link Pageable}.
     *
     * @param paginationRequestDTO the pagination request DTO to map
     * @return the mapped pageable object
     */
    Pageable paginationRequestToPageable(PaginationRequestDTO paginationRequestDTO);

}

