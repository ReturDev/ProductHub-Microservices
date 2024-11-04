package com.returdev.product.services.product;

import com.returdev.product.entities.DimensionsEntity;
import com.returdev.product.entities.ModelEntity;
import com.returdev.product.entities.ProductEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

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
    Optional<ProductEntity> getProductById(Long id);

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
     * Retrieves a list of supplier IDs associated with a given product.
     *
     * @param productId the unique identifier of the product
     * @return a Page of supplier IDs linked to the specified product
     */
    Page<Long> getSupplierIdsByProductId(Long productId, Pageable pageable);

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
     * Searches for products based on the provided example.
     *
     * @param example an Example containing the search criteria
     * @param includeHidden whether to show hidden products
     * @param pageable pagination information
     * @return a Page of ProductEntity matching the example
     */
    Page<ProductEntity> searchProducts(Example<ProductEntity> example, boolean includeHidden, Pageable pageable);

    /**
     * Updates the details of an existing product.
     *
     * @param product the ProductEntity with updated details
     * @return an Optional containing the updated ProductEntity
     */
    Optional<ProductEntity> updateProduct(ProductEntity product);

    /**
     * Updates the name of a product identified by its ID.
     *
     * @param productId the unique identifier of the product
     * @param newName the new name for the product
     * @return an Optional containing the updated ProductEntity
     */
    Optional<ProductEntity> updateProductName(Long productId, String newName);

    /**
     * Updates the summary of a product identified by its ID.
     *
     * @param productId the unique identifier of the product
     * @param newSummary the new summary for the product
     * @return an Optional containing the updated ProductEntity
     */
    Optional<ProductEntity> updateProductSummary(Long productId, String newSummary);

    /**
     * Updates the product code of a product identified by its ID.
     *
     * @param productId the unique identifier of the product
     * @param newCode the new product code for the product
     * @return an Optional containing the updated ProductEntity
     */
    Optional<ProductEntity> updateProductCode(Long productId, String newCode);

    /**
     * Updates the barcode of a product identified by its ID.
     *
     * @param productId the unique identifier of the product
     * @param barcode the new barcode for the product
     * @return an Optional containing the updated ProductEntity
     */
    Optional<ProductEntity> updateProductBarcode(Long productId, String barcode);

    /**
     * Updates the model associated with a product.
     *
     * @param productId the unique identifier of the product
     * @param model the new ModelEntity to associate with the product
     * @return an Optional containing the updated ProductEntity
     */
    Optional<ProductEntity> updateProductModel(Long productId, ModelEntity model);

    /**
     * Updates the dimensions of a product.
     *
     * @param productId the unique identifier of the product
     * @param dimensions the new dimensions to associate with the product
     * @return an Optional containing the updated ProductEntity
     */
    Optional<ProductEntity> updateProductDimensions(Long productId, DimensionsEntity dimensions);

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

