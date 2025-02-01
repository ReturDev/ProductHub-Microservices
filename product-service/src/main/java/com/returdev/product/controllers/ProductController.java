package com.returdev.product.controllers;

import com.returdev.product.dtos.request.PaginationRequestDTO;
import com.returdev.product.dtos.request.ProductRequestDTO;
import com.returdev.product.dtos.response.ProductResponseDTO;
import com.returdev.product.dtos.response.wrappers.ContentResponseWrapper;
import com.returdev.product.dtos.response.wrappers.PaginationResponseWrapper;
import com.returdev.product.entities.ProductEntity;
import com.returdev.product.mappers.EntityDtoMapper;
import com.returdev.product.services.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing products in the application.
 * <p>
 * This controller provides a set of endpoints to perform CRUD operations on products, including retrieving,
 * creating, updating, and deleting products, as well as filtering them by various attributes such as product code,
 * barcode, supplier, category, and more.
 * </p>
 */
@RestController
@RequestMapping("/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final EntityDtoMapper entityDtoMapper;

    /**
     * Retrieves a product by its ID.
     *
     * @param productId the ID of the product to retrieve
     * @return the product details wrapped in a {@link ContentResponseWrapper}
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContentResponseWrapper<ProductResponseDTO> getProductById(
            @PathVariable("id") Long productId
    ){
        return entityDtoMapper.productEntityToContentResponse(
                productService.getProductById(productId)
        );
    }

    /**
     * Retrieves a paginated list of all products.
     *
     * @param pagination the pagination information
     * @param includeHidden flag to indicate if hidden products should be included
     * @return a paginated list of products wrapped in a {@link PaginationResponseWrapper}
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public PaginationResponseWrapper<ProductResponseDTO> getProducts(
            @Valid PaginationRequestDTO pagination,
            @RequestParam(value = "showHidden", defaultValue = "false") boolean includeHidden
    ) {

        return entityDtoMapper.productEntityPageToPaginationResponse(
                productService.getAllProducts(
                        includeHidden,
                        entityDtoMapper.paginationRequestToPageable(pagination)
                )
        );
    }

    /**
     * Retrieves a paginated list of products filtered by product code.
     *
     * @param productCode the product code to filter products by
     * @param includeHidden flag to indicate if hidden products should be included
     * @param pagination the pagination information
     * @return a paginated list of products filtered by product code, wrapped in a {@link PaginationResponseWrapper}
     */
    @GetMapping("/code/{code}")
    @ResponseStatus(HttpStatus.OK)
    public PaginationResponseWrapper<ProductResponseDTO> getProductsByCode(
            @PathVariable("code") String productCode,
            @RequestParam(value = "showHidden", defaultValue = "false") boolean includeHidden,
            @Valid PaginationRequestDTO pagination
    ) {

        return entityDtoMapper.productEntityPageToPaginationResponse(
                productService.getProductByCode(
                        productCode,
                        includeHidden,
                        entityDtoMapper.paginationRequestToPageable(pagination)
                )
        );
    }

    /**
     * Retrieves a paginated list of products filtered by barcode.
     *
     * @param productBarcode the barcode to filter products by
     * @param includeHidden flag to indicate if hidden products should be included
     * @param pagination the pagination information
     * @return a paginated list of products filtered by barcode, wrapped in a {@link PaginationResponseWrapper}
     */
    @GetMapping("/barcode/{barcode}")
    @ResponseStatus(HttpStatus.OK)
    public PaginationResponseWrapper<ProductResponseDTO> getProductsByBarCode(
            @PathVariable("barcode") String productBarcode,
            @RequestParam(value = "showHidden", defaultValue = "false") boolean includeHidden,
            @Valid PaginationRequestDTO pagination
    ) {

        return entityDtoMapper.productEntityPageToPaginationResponse(
                productService.getProductByBarCode(
                        productBarcode,
                        includeHidden,
                        entityDtoMapper.paginationRequestToPageable(pagination)
                )
        );
    }

    /**
     * Retrieves a paginated list of products filtered by product name.
     * Supports searching for products starting with or containing a name.
     *
     * @param name the name of the product to search for
     * @param includeHidden flag to indicate if hidden products should be included
     * @param pagination the pagination information
     * @return a paginated list of products filtered by name, wrapped in a {@link PaginationResponseWrapper}
     */
    @GetMapping("/search-name")
    @ResponseStatus(HttpStatus.OK)
    public PaginationResponseWrapper<ProductResponseDTO> getProductsByName(
            @RequestParam() String name,
            @RequestParam(value = "showHidden", defaultValue = "false") boolean includeHidden,
            @Valid PaginationRequestDTO pagination
    ) {
        Pageable pageable = entityDtoMapper.paginationRequestToPageable(pagination);
        Page<ProductEntity> productPage;

        if (name.startsWith("*")) {
            productPage = productService.getProductByNameContaining(name.replace("*", ""), includeHidden, pageable);
        } else {
            productPage = productService.getProductByNameStartingWith(name, includeHidden, pageable);
        }

        return entityDtoMapper.productEntityPageToPaginationResponse(productPage);
    }

    /**
     * Retrieves a paginated list of products by supplier ID.
     *
     * @param supplierId the ID of the supplier to filter products by
     * @param includeHidden flag to indicate if hidden products should be included
     * @param pagination the pagination information
     * @return a paginated list of products for the specified supplier, wrapped in a {@link PaginationResponseWrapper}
     */
    @GetMapping("/supplier/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PaginationResponseWrapper<ProductResponseDTO> getProductsBySupplierId(
            @PathVariable("id") Long supplierId,
            @RequestParam(value = "showHidden", defaultValue = "false") boolean includeHidden,
            @Valid PaginationRequestDTO pagination
    ){

        return entityDtoMapper.productEntityPageToPaginationResponse(
                productService.getProductsBySupplierId(
                        supplierId,
                        includeHidden,
                        entityDtoMapper.paginationRequestToPageable(pagination)
                )
        );
    }

    /**
     * Retrieves a paginated list of products by category ID.
     *
     * @param categoryId the ID of the category to filter products by
     * @param includeHidden flag to indicate if hidden products should be included
     * @param pagination the pagination information
     * @return a paginated list of products for the specified category, wrapped in a {@link PaginationResponseWrapper}
     */
    @GetMapping("/category/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PaginationResponseWrapper<ProductResponseDTO> getProductsByCategoryId(
            @PathVariable("id") Long categoryId,
            @RequestParam(value = "showHidden", defaultValue = "false") boolean includeHidden,
            @Valid PaginationRequestDTO pagination
    ){

        return entityDtoMapper.productEntityPageToPaginationResponse(
                productService.getProductsByCategoryId(
                        categoryId,
                        includeHidden,
                        entityDtoMapper.paginationRequestToPageable(pagination)
                )
        );
    }

    /**
     * Retrieves a paginated list of products by model ID.
     *
     * @param modelId the ID of the model to filter products by
     * @param includeHidden flag to indicate if hidden products should be included
     * @param pagination the pagination information
     * @return a paginated list of products for the specified model, wrapped in a {@link PaginationResponseWrapper}
     */
    @GetMapping("/model/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PaginationResponseWrapper<ProductResponseDTO> getProductsByModelId(
            @PathVariable("id") Long modelId,
            @RequestParam(value = "showHidden", defaultValue = "false") boolean includeHidden,
            @Valid PaginationRequestDTO pagination
    ) {

        return entityDtoMapper.productEntityPageToPaginationResponse(
                productService.getProductsByModelId(
                        modelId,
                        includeHidden,
                        entityDtoMapper.paginationRequestToPageable(pagination)
                )
        );
    }

    /**
     * Searches for products based on the provided criteria in the {@link ProductRequestDTO}.
     *
     * @param productRequestDTO the criteria used to filter products
     * @param includeHidden flag to indicate if hidden products should be included
     * @param pagination the pagination information
     * @return a paginated list of products that match the search criteria, wrapped in a {@link PaginationResponseWrapper}
     */
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public PaginationResponseWrapper<ProductResponseDTO> searchProduct(
            ProductRequestDTO productRequestDTO,
            @RequestParam(value = "showHidden", defaultValue = "false") boolean includeHidden,
            @Valid PaginationRequestDTO pagination
    ) {

        return entityDtoMapper.productEntityPageToPaginationResponse(
                productService.searchProducts(
                        entityDtoMapper.productRequestToEntity(productRequestDTO),
                        includeHidden,
                        entityDtoMapper.paginationRequestToPageable(pagination)
                )
        );
    }

    /**
     * Updates the product information.
     *
     * @param productRequestDTO the product data to update
     * @return the updated product details wrapped in a {@link ContentResponseWrapper}
     */
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ContentResponseWrapper<ProductResponseDTO> updateProduct(
            @RequestBody @Valid ProductRequestDTO productRequestDTO
    ) {

        return entityDtoMapper.productEntityToContentResponse(
                productService.updateProduct(
                        entityDtoMapper.productRequestToEntity(productRequestDTO)
                )
        );
    }

    /**
     * Partially updates product properties.
     *
     * @param productRequestDTO the product data to update
     * @return the updated product details wrapped in a {@link ContentResponseWrapper}
     */
    @PatchMapping()
    @ResponseStatus(HttpStatus.OK)
    public ContentResponseWrapper<ProductResponseDTO> updateProductProperty(
            @RequestBody ProductRequestDTO productRequestDTO
    ) {

        return entityDtoMapper.productEntityToContentResponse(
                productService.updateProduct(
                        productRequestDTO.id(),
                        productRequestDTO.name(),
                        productRequestDTO.summary(),
                        productRequestDTO.productCode(),
                        productRequestDTO.barcode(),
                        entityDtoMapper.dimensionRequestToEntity(productRequestDTO.dimensions())
                )
        );
    }

    /**
     * Partially updates the product model.
     *
     * @param productId the ID of the product to update
     * @param modelId the ID of the model to assign to the product
     * @return the updated product details wrapped in a {@link ContentResponseWrapper}
     */
    @PatchMapping("/{productId}/{modelId}")
    @ResponseStatus(HttpStatus.OK)
    public ContentResponseWrapper<ProductResponseDTO> updateProductModel(
            @PathVariable("productId") Long productId,
            @PathVariable("modelId") Long modelId
    ) {

        return entityDtoMapper.productEntityToContentResponse(
                productService.updateProductModel(productId, modelId)
        );
    }

    /**
     * Hides a product.
     *
     * @param productId the ID of the product to hide
     */
    @PatchMapping("/{id}/hide")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void hideProduct(
            @PathVariable("id") Long productId
    ) {
        productService.hideProduct(productId);
    }

    /**
     * Unhides a product.
     *
     * @param productId the ID of the product to unhide
     */
    @PatchMapping("/{id}/unhide")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unhideProduct(
            @PathVariable("id") Long productId
    ) {
        productService.unhideProduct(productId);
    }

    /**
     * Adds a supplier to a product.
     *
     * @param productId the ID of the product
     * @param supplierId the ID of the supplier
     */
    @PatchMapping("/{productId}/supplier/{supplierId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addProductSupplier(
            @PathVariable("productId") Long productId,
            @PathVariable("supplierId") Long supplierId
    ) {

        productService.addProductSupplier(
                productId,
                supplierId
        );
    }

    /**
     * Removes a supplier from a product.
     *
     * @param productId the ID of the product
     * @param supplierId the ID of the supplier to remove
     */
    @DeleteMapping("/{productId}/supplier/{supplierId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProductSupplier(
            @PathVariable("productId") Long productId,
            @PathVariable("supplierId") Long supplierId
    ) {

        productService.removeProductSupplier(
                productId,
                supplierId
        );
    }

    /**
     * Saves a new product.
     *
     * @param productRequestDTO the product data to save
     * @return the saved product details wrapped in a {@link ContentResponseWrapper}
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ContentResponseWrapper<ProductResponseDTO> saveProduct(
            @RequestBody @Valid ProductRequestDTO productRequestDTO
    ) {

        return entityDtoMapper.productEntityToContentResponse(
                productService.saveProduct(
                        entityDtoMapper.productRequestToEntity(
                                productRequestDTO
                        )
                )
        );
    }
}

