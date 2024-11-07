package com.returdev.product.services.product;

import com.returdev.product.entities.DimensionsEntity;
import com.returdev.product.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    ProductEntity getProductById(Long id);

    /**
     * Retrieves a paginated list of products by product code.
     *
     * @param code the unique code of the product
     * @param includeHidden whether to include hidden products in the result
     * @param pageable pagination information
     * @return a Page of ProductEntity matching the specified code
     */
    Page<ProductEntity> getProductByCode(String code, boolean includeHidden, Pageable pageable);

    /**
     * Retrieves a paginated list of products by barcode.
     *
     * @param barcode the unique barcode of the product
     * @param includeHidden whether to include hidden products in the result
     * @param pageable pagination information
     * @return a Page of ProductEntity matching the specified barcode
     */
    Page<ProductEntity> getProductByBarCode(String barcode, boolean includeHidden, Pageable pageable);

    /**
     * Retrieves a paginated list of products whose names contain a specified string.
     *
     * @param name the substring to search for within product names
     * @param includeHidden whether to include hidden products in the result
     * @param pageable pagination information
     * @return a Page of ProductEntity whose names contain the specified string
     */
    Page<ProductEntity> getProductByNameContaining(String name, boolean includeHidden, Pageable pageable);

    /**
     * Retrieves a paginated list of products whose names start with a specified string.
     *
     * @param name the prefix to search for within product names
     * @param includeHidden whether to include hidden products in the result
     * @param pageable pagination information
     * @return a Page of ProductEntity whose names start with the specified string
     */
    Page<ProductEntity> getProductByNameStartingWith(String name, boolean includeHidden, Pageable pageable);


    /**
     * Retrieves a paginated list of products associated with a specific supplier.
     *
     * @param supplierId the unique identifier of the supplier whose products are to be retrieved
     * @param includeHidden a flag indicating whether to include hidden products (true to include, false to exclude)
     * @param pageable pagination information, including page number and size
     * @return a Page of ProductEntity containing products associated with the specified supplier
     */
    Page<ProductEntity> getProductsBySupplierId(Long supplierId, boolean includeHidden, Pageable pageable);

    /**
     * Retrieves a paginated list of products by category ID.
     *
     * @param categoryId the unique identifier of the category
     * @param includeHidden whether to include hidden products in the result
     * @param pageable pagination information
     * @return a Page of ProductEntity belonging to the specified category
     */
    Page<ProductEntity> getProductsByCategoryId(Long categoryId, boolean includeHidden, Pageable pageable);

    /**
     * Retrieves a paginated list of products by model ID.
     *
     * @param modelId the unique identifier of the model
     * @param includeHidden whether to include hidden products in the result
     * @param pageable pagination information
     * @return a Page of ProductEntity associated with the specified model
     */
    Page<ProductEntity> getProductsByModelId(Long modelId, boolean includeHidden, Pageable pageable);

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
    Page<ProductEntity> searchProducts(ProductEntity productEntity, boolean includeHidden, Pageable pageable);

    /**
     * Updates the details of an existing product.
     *
     * @param product the ProductEntity with updated details
     * @return the updated ProductEntity
     */
    ProductEntity updateProduct(ProductEntity product);

    /**
     * Updates the name of a product identified by its ID.
     *
     * @param productId the unique identifier of the product
     * @param newName   the new name for the product
     * @return an Optional containing the updated ProductEntity
     */
    ProductEntity updateProductName(Long productId, String newName);

    /**
     * Updates the summary of a product identified by its ID.
     *
     * @param productId  the unique identifier of the product
     * @param newSummary the new summary for the product
     * @return an Optional containing the updated ProductEntity
     */
    ProductEntity updateProductSummary(Long productId, String newSummary);

    /**
     * Updates the product code of a product identified by its ID.
     *
     * @param productId the unique identifier of the product
     * @param newCode   the new product code for the product
     * @return an Optional containing the updated ProductEntity
     */
    ProductEntity updateProductCode(Long productId, String newCode);

    /**
     * Updates the barcode of a product identified by its ID.
     *
     * @param productId the unique identifier of the product
     * @param barcode   the new barcode for the product
     * @return an Optional containing the updated ProductEntity
     */
    ProductEntity updateProductBarcode(Long productId, String barcode);

    /**
     * Updates the model associated with a product identified by the given product ID.
     *
     * @param productId the unique identifier of the product to be updated
     * @param modelId   the unique identifier of the new model to associate with the product
     * @return an Optional containing the updated ProductEntity if the update is successful,
     * or an empty Optional if the product does not exist or the update fails
     */
    ProductEntity updateProductModel(Long productId, Long modelId);

    /**
     * Updates the dimensions of a product.
     *
     * @param productId  the unique identifier of the product
     * @param dimensions the new dimensions to associate with the product
     * @return an Optional containing the updated ProductEntity
     */
    ProductEntity updateProductDimensions(Long productId, DimensionsEntity dimensions);

    /**
     * Adds a new supplier association to a product.
     *
     * @param productId the unique identifier of the product
     * @param newSupplierId the unique identifier of the new supplier to add
     */
    void addProductSupplier(Long productId, Long newSupplierId);

    /**
     * Removes an existing supplier association from a product.
     *
     * @param productId the unique identifier of the product
     * @param supplierId the unique identifier of the supplier to remove
     */
    void removeProductSupplier(Long productId, Long supplierId);

    /**
     * Marks a product as hidden.
     *
     * @param productId the unique identifier of the product
     */
    void hideProduct(Long productId);

    /**
     * Marks a product as visible.
     *
     * @param productId the unique identifier of the product
     */
    void unhideProduct(Long productId);

    /**
     * Saves a new product to the system.
     *
     * @param product the ProductEntity to be saved
     * @return the saved ProductEntity
     */
    ProductEntity saveProduct(ProductEntity product);

}

