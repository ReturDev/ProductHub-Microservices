package com.returdev.product.service.product;

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

    // GETS

    /**
     * Retrieves a product by its ID.
     *
     * @param id the unique identifier of the product
     * @return an Optional containing the found ProductEntity, or empty if not found
     */
    Optional<ProductEntity> getProductById(Long id);

    /**
     * Retrieves a list of products by their product code.
     *
     * @param code the unique product code
     * @param includeHidden whether to include hidden products in the result
     * @return a list of ProductEntity matching the provided code
     */
    List<ProductEntity> getProductByCode(String code, boolean includeHidden);

    /**
     * Retrieves a list of products by their barcode.
     *
     * @param barcode the barcode of the product
     * @param includeHidden whether to include hidden products in the result
     * @return a list of ProductEntity matching the provided barcode
     */
    List<ProductEntity> getProductByBarCode(String barcode, boolean includeHidden);

    /**
     * Retrieves a paginated list of products whose names contain the specified string.
     *
     * @param name the string to search within product names
     * @param pageable pagination information
     * @param includeHidden whether to include hidden products in the result
     * @return a Page of ProductEntity containing matching products
     */
    Page<ProductEntity> getProductByNameContaining(String name, Pageable pageable, boolean includeHidden);

    /**
     * Retrieves the supplier IDs associated with a specific product.
     *
     * @param productId the unique identifier of the product
     * @return an Optional containing a list of supplier IDs, or empty if none found
     */
    Optional<Long> getSupplierIdsByProductId(Long productId);

    /**
     * Retrieves a paginated list of products by category ID.
     *
     * @param categoryId the unique identifier of the category
     * @return a Page of ProductEntity belonging to the specified category
     */
    Page<ProductEntity> getProductsByCategoryId(Long categoryId);

    /**
     * Retrieves a paginated list of products by model ID.
     *
     * @param modelId the unique identifier of the model
     * @param pageable pagination information
     * @param includeHidden whether to include hidden products in the result
     * @return a Page of ProductEntity associated with the specified model
     */
    Page<ProductEntity> getProductsByModelId(Long modelId, Pageable pageable, boolean includeHidden);

    /**
     * Retrieves a paginated list of all products.
     *
     * @param pageable pagination information
     * @param includeHidden whether to include hidden products in the result
     * @return a Page of all ProductEntity
     */
    Page<ProductEntity> getAllProducts(Pageable pageable, boolean includeHidden);

    /**
     * Searches for products based on the provided example.
     *
     * @param example an Example containing the search criteria
     * @param pageable pagination information
     * @param includeHidden whether to show hidden products
     * @return a Page of ProductEntity matching the example
     */
    Page<ProductEntity> searchProducts(Example<ProductEntity> example, Pageable pageable, boolean includeHidden);

    // UPDATES

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
     * @param newName the new name for the product
     * @return the updated ProductEntity
     */
    ProductEntity updateProductName(Long productId, String newName);

    /**
     * Updates the summary of a product identified by its ID.
     *
     * @param productId the unique identifier of the product
     * @param newSummary the new summary for the product
     * @return the updated ProductEntity
     */
    ProductEntity updateProductSummary(Long productId, String newSummary);

    /**
     * Updates the product code of a product identified by its ID.
     *
     * @param productId the unique identifier of the product
     * @param newCode the new product code for the product
     * @return the updated ProductEntity
     */
    ProductEntity updateProductCode(Long productId, String newCode);

    /**
     * Updates the barcode of a product identified by its ID.
     *
     * @param productId the unique identifier of the product
     * @param barcode the new barcode for the product
     * @return the updated ProductEntity
     */
    ProductEntity updateProductBarcode(Long productId, String barcode);

    /**
     * Updates the model associated with a product.
     *
     * @param productId the unique identifier of the product
     * @param model the new ModelEntity to associate with the product
     * @return the updated ProductEntity
     */
    ProductEntity updateProductModel(Long productId, ModelEntity model);

    /**
     * Removes a supplier association from a product.
     *
     * @param productId the unique identifier of the product
     * @param supplierId the unique identifier of the supplier to remove
     */
    void removeProductSupplier(Long productId, Long supplierId);

    /**
     * Adds a supplier association to a product.
     *
     * @param productId the unique identifier of the product
     * @param newSupplierId the unique identifier of the new supplier to add
     */
    void addProductSupplier(Long productId, Long newSupplierId);

    // SAVE

    /**
     * Saves a new product to the system.
     *
     * @param product the ProductEntity to be saved
     * @return the saved ProductEntity
     */
    ProductEntity saveProduct(ProductEntity product);

    /**
     * Activates a product identified by its ID.
     *
     * @param productId the unique identifier of the product
     * @return the activated ProductEntity
     */
    ProductEntity activateProduct(Long productId);

    // DELETE

    /**
     * Deactivates a product identified by its ID.
     *
     * @param productId the unique identifier of the product
     */
    void deactivateProduct(Long productId);

    /**
     * Permanently deletes a product identified by its ID.
     *
     * @param productId the unique identifier of the product
     */
    void deleteProductPermanently(Long productId);
}

