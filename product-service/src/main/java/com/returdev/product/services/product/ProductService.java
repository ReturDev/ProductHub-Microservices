package com.returdev.product.services.product;

import com.returdev.product.entities.DimensionsEntity;
import com.returdev.product.entities.ProductEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service interface for managing products in the system.
 * Provides methods for retrieving, updating, saving, and deleting product entities.
 *
 * <p>This interface defines the operations available for managing product entities,
 * including various retrieval methods that support filtering and pagination,
 * as well as methods for updating, saving, and deleting products.</p>
 */
public interface ProductService {

    /**
     * Retrieves a product by its ID.
     *
     * @param id the unique identifier of the product
     * @return an Optional containing the found ProductEntity, or empty if not found
     */
    ProductEntity getProductById(@NotNull(message = "{validation.not_null.message}") Long id);

    /**
     * Retrieves a paginated list of products by product code.
     *
     * @param code the unique code of the product
     * @param includeHidden whether to include hidden products in the result
     * @param pageable pagination information
     * @return a Page of ProductEntity matching the specified code
     */
    Page<ProductEntity> getProductByCode(
            @NotBlank(message = "{validation.not_blank.message}")
            @Size(min = 3, max = 20, message = "{validation.size.message}")
            String code,
            boolean includeHidden,
            Pageable pageable
    );

    /**
     * Retrieves a paginated list of products by barcode.
     *
     * @param barcode the unique barcode of the product
     * @param includeHidden whether to include hidden products in the result
     * @param pageable pagination information
     * @return a Page of ProductEntity matching the specified barcode
     */
    Page<ProductEntity> getProductByBarCode(
            @NotBlank(message = "{validation.not_blank.message}")
            @Size(min = 8, max = 30, message = "{validation.size.message}")
            String barcode,
            boolean includeHidden,
            Pageable pageable
    );

    /**
     * Retrieves a paginated list of products whose names contain a specified string.
     *
     * @param name the substring to search for within product names
     * @param includeHidden whether to include hidden products in the result
     * @param pageable pagination information
     * @return a Page of ProductEntity whose names contain the specified string
     */
    Page<ProductEntity> getProductByNameContaining(
            @NotBlank(message = "{validation.not_blank.message}")
            String name,
            boolean includeHidden,
            Pageable pageable
    );

    /**
     * Retrieves a paginated list of products whose names start with a specified string.
     *
     * @param name the prefix to search for within product names
     * @param includeHidden whether to include hidden products in the result
     * @param pageable pagination information
     * @return a Page of ProductEntity whose names start with the specified string
     */
    Page<ProductEntity> getProductByNameStartingWith(
            @NotBlank(message = "{validation.not_blank.message}") String name,
            boolean includeHidden,
            Pageable pageable
    );


    /**
     * Retrieves a paginated list of products associated with a specific supplier.
     *
     * @param supplierId the unique identifier of the supplier whose products are to be retrieved
     * @param includeHidden a flag indicating whether to include hidden products (true to include, false to exclude)
     * @param pageable pagination information, including page number and size
     * @return a Page of ProductEntity containing products associated with the specified supplier
     */
    Page<ProductEntity> getProductsBySupplierId(
            @NotNull(message = "{validation.not_null.message}") Long supplierId,
            boolean includeHidden,
            Pageable pageable
    );

    /**
     * Retrieves a paginated list of products by category ID.
     *
     * @param categoryId the unique identifier of the category
     * @param includeHidden whether to include hidden products in the result
     * @param pageable pagination information
     * @return a Page of ProductEntity belonging to the specified category
     */
    Page<ProductEntity> getProductsByCategoryId(
            @NotNull(message = "{validation.not_null.message}") Long categoryId,
            boolean includeHidden,
            Pageable pageable
    );

    /**
     * Retrieves a paginated list of products by model ID.
     *
     * @param modelId the unique identifier of the model
     * @param includeHidden whether to include hidden products in the result
     * @param pageable pagination information
     * @return a Page of ProductEntity associated with the specified model
     */
    Page<ProductEntity> getProductsByModelId(
            @NotNull(message = "{validation.not_null.message}") Long modelId,
            boolean includeHidden,
            Pageable pageable
    );

    /**
     * Retrieves a paginated list of all products.
     *
     * @param includeHidden whether to include hidden products in the result
     * @param pageable pagination information
     * @return a Page of all ProductEntity
     */
    Page<ProductEntity> getAllProducts(boolean includeHidden, Pageable pageable);

    /**
     * Searches for products based on the attributes of the provided ProductEntity.
     *
     * @param productEntity an instance of ProductEntity containing the search criteria; fields
     *                      set to null will be ignored in the search
     * @param includeHidden a flag indicating whether to include hidden products in the search
     *                      results (true to include, false to exclude)
     * @param pageable pagination information, including the requested page number and size
     * @return a Page of ProductEntity containing products that match the search criteria
     */
    Page<ProductEntity> searchProducts(
            @Valid ProductEntity productEntity,
            boolean includeHidden,
            Pageable pageable
    );

    /**
     * Updates the details of an existing product.
     *
     * @param product the ProductEntity with updated details
     * @return the updated ProductEntity
     */
    @Transactional
    ProductEntity updateProduct(@Valid ProductEntity product);

    /**
     * Updates an existing product with new values for its properties.
     * This method is transactional and will ensure that all updates are committed atomically.
     *
     * @param productId the ID of the product to update (must not be null)
     * @param newName the new name of the product (must not be blank and must have a length between 3 and 50 characters)
     * @param newSummary the new summary or description of the product (must not be null and must not exceed 150 characters)
     * @param newCode the new code for the product (must not be empty and must have a length between 3 and 20 characters)
     * @param newBarcode the new barcode for the product (must not be null and must have a length between 8 and 30 characters)
     * @param newDimensions the new dimensions of the product (must not be null and must be a valid DimensionsEntity)
     * @return the updated {@link ProductEntity} after applying the changes
     */
    @Transactional
    public ProductEntity updateProduct(
            @NotNull(message = "{validation.not_null.message}")
            Long productId,

            @NotBlank(message = "{validation.not_blank.message}")
            @Size(min = 3, max = 50, message = "{validation.size.message}")
            String newName,

            @NotNull(message = "{validation.not_null.message}")
            @Size(max = 150, message = "{validation.size.max.message}")
            String newSummary,

            @NotEmpty(message = "{validation.not_empty.message}")
            @Size(min = 3, max = 20, message = "{validation.size.message}")
            String newCode,

            @NotNull(message = "{validation.not_null.message}")
            @Size(min = 8, max = 30, message = "{validation.size.message}")
            String newBarcode,

            @NotNull(message = "{validation.not_null.message}")
            @Valid
            DimensionsEntity newDimensions
    );


    /**
     * Updates the model associated with a product identified by the given product ID.
     *
     * @param productId the unique identifier of the product to be updated
     * @param modelId   the unique identifier of the new model to associate with the product
     * @return an Optional containing the updated ProductEntity if the update is successful,
     * or an empty Optional if the product does not exist or the update fails
     */
    ProductEntity updateProductModel(
            @NotNull(message = "{validation.not_null.message}") Long productId,
            @NotNull(message = "{validation.not_null.message}") Long modelId);

    /**
     * Adds a new supplier association to a product.
     *
     * @param productId the unique identifier of the product
     * @param newSupplierId the unique identifier of the new supplier to add
     */
    void addProductSupplier(
            @NotNull(message = "{validation.not_null.message}") Long productId,
            @NotNull(message = "{validation.not_null.message}") Long newSupplierId
    );

    /**
     * Removes an existing supplier association from a product.
     *
     * @param productId the unique identifier of the product
     * @param supplierId the unique identifier of the supplier to remove
     */
    void removeProductSupplier(
            @NotNull(message = "{validation.not_null.message}") Long productId,
            @NotNull(message = "{validation.not_null.message}") Long supplierId
    );

    /**
     * Marks a product as hidden.
     *
     * @param productId the unique identifier of the product
     */
    void hideProduct(@NotNull(message = "{validation.not_null.message}") Long productId);

    /**
     * Marks a product as visible.
     *
     * @param productId the unique identifier of the product
     */
    void unhideProduct(@NotNull(message = "{validation.not_null.message}") Long productId);

    /**
     * Saves a new product to the system.
     *
     * @param product the ProductEntity to be saved
     * @return the saved ProductEntity
     */
    ProductEntity saveProduct(@Valid ProductEntity product);

}

