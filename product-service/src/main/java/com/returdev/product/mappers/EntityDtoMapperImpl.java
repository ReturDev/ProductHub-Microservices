package com.returdev.product.mappers;

import com.returdev.product.dtos.request.*;
import com.returdev.product.dtos.response.*;
import com.returdev.product.dtos.response.wrappers.ContentResponseWrapper;
import com.returdev.product.dtos.response.wrappers.PaginationResponseWrapper;
import com.returdev.product.entities.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link EntityDtoMapper} for mapping between entity and DTO objects.
 */
@Component
public class EntityDtoMapperImpl implements EntityDtoMapper {

    /** {@inheritDoc} */
    @Override
    public ContentResponseWrapper<BrandResponseDTO> brandEntityToContentResponse(BrandEntity brandEntity) {
        return new ContentResponseWrapper<>(
                brandEntityToBrandResponse(brandEntity)
        );
    }

    /** {@inheritDoc} */
    @Override
    public PaginationResponseWrapper<BrandResponseDTO> brandEntityPageToPaginationResponse(Page<BrandEntity> brandEntityPage) {
        return new PaginationResponseWrapper<>(
                brandEntityPage.getContent().stream().map(this::brandEntityToBrandResponse).toList(),
                pageToPageInfo(brandEntityPage)
        );
    }

    /** {@inheritDoc} */
    @Override
    public BrandEntity brandRequestToEntity(BrandRequestDTO brandRequestDTO) {
        return new BrandEntity(
                brandRequestDTO.id(),
                brandRequestDTO.name(),
                brandRequestDTO.summary(),
                brandRequestDTO.isActive()
        );
    }

    /** {@inheritDoc} */
    @Override
    public ContentResponseWrapper<CategoryResponseDTO> categoryEntityToContentResponse(CategoryEntity categoryEntity) {
        return new ContentResponseWrapper<>(
                categoryEntityToCategoryResponse(categoryEntity)
        );
    }

    /** {@inheritDoc} */
    @Override
    public PaginationResponseWrapper<CategoryResponseDTO> categoryEntityPageToPaginationResponse(Page<CategoryEntity> categoryEntityPage) {
        return new PaginationResponseWrapper<>(
                categoryEntityPage.getContent().stream().map(this::categoryEntityToCategoryResponse).toList(),
                pageToPageInfo(categoryEntityPage)
        );
    }

    /** {@inheritDoc} */
    @Override
    public CategoryEntity categoryRequestToEntity(CategoryRequestDTO categoryRequestDTO) {
        return new CategoryEntity(
                categoryRequestDTO.id(),
                categoryRequestDTO.name(),
                categoryRequestDTO.summary()
        );
    }

    /** {@inheritDoc} */
    @Override
    public ContentResponseWrapper<SupplierResponseDTO.ContactInfoResponseDTO> contactInfoEntityToContentResponse(ContactInfoEntity contactInfoEntity) {
        return new ContentResponseWrapper<>(
                contactInfoEntityToContactInfoResponse(contactInfoEntity)
        );
    }

    /** {@inheritDoc} */
    @Override
    public ContactInfoEntity contactInfoRequestToEntity(ContactInfoRequestDTO contactInfoRequestDTO) {
        SupplierEntity supplierEntity = new SupplierEntity();
        supplierEntity.setId(contactInfoRequestDTO.id());

        return new ContactInfoEntity(
                contactInfoRequestDTO.id(),
                contactInfoRequestDTO.name(),
                contactInfoRequestDTO.observations(),
                contactInfoRequestDTO.phoneNumber(),
                contactInfoRequestDTO.email(),
                contactInfoRequestDTO.isCommercial(),
                supplierEntity
        );
    }

    /** {@inheritDoc} */
    @Override
    public ContentResponseWrapper<ModelResponseDTO> modelEntityToContentResponse(ModelEntity modelEntity) {
        return new ContentResponseWrapper<>(
                modelEntityToModelResponse(modelEntity)
        );
    }

    /** {@inheritDoc} */
    @Override
    public PaginationResponseWrapper<ModelResponseDTO> modelEntityPageToPaginationResponse(Page<ModelEntity> modelEntityPage) {
        return new PaginationResponseWrapper<>(
                modelEntityPage.getContent().stream().map(this::modelEntityToModelResponse).toList(),
                pageToPageInfo(modelEntityPage)
        );
    }

    /** {@inheritDoc} */
    @Override
    public ModelEntity modelRequestToEntity(ModelRequestDTO modelRequestDTO) {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setId(modelRequestDTO.brandId());

        return new ModelEntity(
                modelRequestDTO.id(),
                modelRequestDTO.name(),
                modelRequestDTO.summary(),
                brandEntity,
                modelRequestDTO.isProductionActive()
        );
    }

    /** {@inheritDoc} */
    @Override
    public ContentResponseWrapper<ProductResponseDTO> productEntityToContentResponse(ProductEntity productEntity) {
        return new ContentResponseWrapper<>(
                productEntityToProductResponse(productEntity)
        );
    }

    /** {@inheritDoc} */
    @Override
    public PaginationResponseWrapper<ProductResponseDTO> productEntityPageToPaginationResponse(Page<ProductEntity> productEntityPage) {
        return new PaginationResponseWrapper<>(
                productEntityPage.getContent().stream().map(this::productEntityToProductResponse).toList(),
                pageToPageInfo(productEntityPage)
        );
    }

    /** {@inheritDoc} */
    @Override
    public ProductEntity productRequestToEntity(ProductRequestDTO productRequestDTO) {
        ModelEntity modelEntity = new ModelEntity();
        modelEntity.setId(productRequestDTO.modelId());

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(productRequestDTO.categoryId());

        List<SupplierEntity> supplierEntities = productRequestDTO.supplierIds().stream().map(supplierId -> {
                    SupplierEntity supplierEntity = new SupplierEntity();
                    supplierEntity.setId(supplierId);
                    return supplierEntity;
                }
        ).toList();


        return new ProductEntity(
                productRequestDTO.id(),
                productRequestDTO.name(),
                productRequestDTO.summary(),
                productRequestDTO.productCode(),
                productRequestDTO.barcode(),
                modelEntity,
                categoryEntity,
                supplierEntities,
                dimensionRequestToEntity(productRequestDTO.dimensions()),
                productRequestDTO.isHidden()
        );
    }

    /** {@inheritDoc} */
    @Override
    public ContentResponseWrapper<SupplierResponseDTO> supplierEntityToContentResponse(SupplierEntity supplierEntity) {
        return new ContentResponseWrapper<>(
                supplierEntityToSupplierResponse(supplierEntity)
        );
    }

    /** {@inheritDoc} */
    @Override
    public PaginationResponseWrapper<SupplierResponseDTO> supplierEntityPageToPaginationResponse(Page<SupplierEntity> supplierEntityPage) {
        return new PaginationResponseWrapper<>(
                supplierEntityPage.getContent().stream().map(this::supplierEntityToSupplierResponse).toList(),
                pageToPageInfo(supplierEntityPage)
        );
    }

    /** {@inheritDoc} */
    @Override
    public SupplierEntity supplierRequestToEntity(SupplierRequestDTO supplierRequestDTO) {
        return new SupplierEntity(
                supplierRequestDTO.id(),
                supplierRequestDTO.name(),
                supplierRequestDTO.observations(),
                null,
                supplierRequestDTO.isActive()
        );
    }

    /** {@inheritDoc} */
    @Override
    public DimensionsEntity dimensionRequestToEntity(ProductRequestDTO.DimensionsRequestDTO dimensionsRequestDTO) {

        DimensionsEntity dimensionsEntity = new DimensionsEntity();
        dimensionsEntity.setHeightCm(dimensionsRequestDTO.heightCm());
        dimensionsEntity.setLengthCm(dimensionsRequestDTO.lengthCm());
        dimensionsEntity.setWeightKg(dimensionsRequestDTO.weightKg());
        dimensionsEntity.setWidthCm(dimensionsRequestDTO.widthCm());

        return dimensionsEntity;
    }

    /** {@inheritDoc} */
    @Override
    public Pageable paginationRequestToPageable(PaginationRequestDTO paginationRequestDTO) {
        Sort sort = Sort.by(
                Sort.Direction.fromString(paginationRequestDTO.sortDirection()),
                paginationRequestDTO.orderBy().toLowerCase()
        );

        return PageRequest.of(
                paginationRequestDTO.page() - 1,
                paginationRequestDTO.pageSize(),
                sort
        );
    }

    /**
     * Converts a {@link Page} object to {@link PaginationResponseWrapper.PageInfo}.
     *
     * @param page the page to convert
     * @param <T> the type of the page content
     * @return a PageInfo object containing pagination details
     */
    private <T> PaginationResponseWrapper.PageInfo pageToPageInfo(Page<T> page) {
        return new PaginationResponseWrapper.PageInfo(
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber() + 1
        );
    }

    /**
     * Maps a {@link BrandEntity} to a {@link BrandResponseDTO}.
     *
     * @param brandEntity the brand entity to map
     * @return a brand response DTO with mapped data
     */
    private BrandResponseDTO brandEntityToBrandResponse(BrandEntity brandEntity) {
        return new BrandResponseDTO(
                brandEntity.getId(),
                brandEntity.getName(),
                brandEntity.getSummary(),
                brandEntity.isActive()
        );
    }

    /**
     * Maps a {@link CategoryEntity} to a {@link CategoryResponseDTO}.
     *
     * @param categoryEntity the category entity to map
     * @return a category response DTO with mapped data
     */
    private CategoryResponseDTO categoryEntityToCategoryResponse(CategoryEntity categoryEntity) {
        return new CategoryResponseDTO(
                categoryEntity.getId(),
                categoryEntity.getName(),
                categoryEntity.getSummary()
        );
    }

    /**
     * Maps a {@link ContactInfoEntity} to a {@link SupplierResponseDTO.ContactInfoResponseDTO}.
     *
     * @param contactInfoEntity the contact info entity to map
     * @return a contact info response DTO with mapped data
     */
    private SupplierResponseDTO.ContactInfoResponseDTO contactInfoEntityToContactInfoResponse(ContactInfoEntity contactInfoEntity) {
        return new SupplierResponseDTO.ContactInfoResponseDTO(
                contactInfoEntity.getId(),
                contactInfoEntity.getName(),
                contactInfoEntity.getObservations(),
                contactInfoEntity.getPhoneNumber(),
                contactInfoEntity.getEmail(),
                contactInfoEntity.isCommercial()
        );
    }

    /**
     * Maps a {@link ModelEntity} to a {@link ModelResponseDTO}.
     *
     * @param modelEntity the model entity to map
     * @return a model response DTO with mapped data
     */
    private ModelResponseDTO modelEntityToModelResponse(ModelEntity modelEntity) {
        return new ModelResponseDTO(
                modelEntity.getId(),
                modelEntity.getName(),
                modelEntity.getSummary(),
                brandEntityToBrandResponse(modelEntity.getBrand()),
                modelEntity.isProductionActive()
        );
    }

    /**
     * Maps a {@link ProductEntity} to a {@link ProductResponseDTO}.
     *
     * @param productEntity the product entity to map
     * @return a product response DTO with mapped data
     */
    private ProductResponseDTO productEntityToProductResponse(ProductEntity productEntity) {
        DimensionsEntity dimensionsEntity = productEntity.getDimensions();

        return new ProductResponseDTO(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getSummary(),
                productEntity.getProductCode(),
                productEntity.getBarcode(),
                modelEntityToModelResponse(productEntity.getModel()),
                categoryEntityToCategoryResponse(productEntity.getCategory()),
                new ProductResponseDTO.DimensionsResponseDTO(
                        dimensionsEntity.getId(),
                        dimensionsEntity.getWeightKg(),
                        dimensionsEntity.getLengthCm(),
                        dimensionsEntity.getWidthCm(),
                        dimensionsEntity.getHeightCm()
                ),
                productEntity.isHidden()
        );
    }

    /**
     * Maps a {@link SupplierEntity} to a {@link SupplierResponseDTO}.
     *
     * @param supplierEntity the supplier entity to map
     * @return a supplier response DTO with mapped data
     */
    private SupplierResponseDTO supplierEntityToSupplierResponse(SupplierEntity supplierEntity) {
        return new SupplierResponseDTO(
                supplierEntity.getId(),
                supplierEntity.getName(),
                supplierEntity.getObservations(),
                supplierEntity.getContacts().stream().map(this::contactInfoEntityToContactInfoResponse).toList(),
                supplierEntity.isActive()
        );
    }

}

