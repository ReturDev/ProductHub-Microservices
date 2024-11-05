package com.returdev.product.repositories;

import com.returdev.product.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Repository interface for managing ProductEntity persistence operations.
 * Provides methods for querying products by various attributes, such as product code,
 * barcode, and name, with options to include or exclude hidden products.
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    /**
     * Finds products by their product code, with an option to include hidden products.
     *
     * @param code the unique product code to search for
     * @param includeHidden whether to include hidden products in the search results
     * @param pageable pagination information
     * @return a Page of ProductEntity matching the specified product code
     */
    @Query("SELECT p FROM ProductEntity p WHERE (:includeHidden = true OR p.isHidden = false) AND p.productCode = :code")
    Page<ProductEntity> findProductsByCode(@Param("code") String code, @Param("includeHidden") boolean includeHidden, Pageable pageable);

    /**
     * Finds products by their barcode, with an option to include hidden products.
     *
     * @param barcode the barcode to search for
     * @param includeHidden whether to include hidden products in the search results
     * @param pageable pagination information
     * @return a Page of ProductEntity matching the specified barcode
     */
    @Query("SELECT p FROM ProductEntity p WHERE (:includeHidden = true OR p.isHidden = false) AND p.barcode = :barcode")
    Page<ProductEntity> findProductsByBarcode(@Param("barcode") String barcode, @Param("includeHidden") boolean includeHidden, Pageable pageable);

    /**
     * Finds products whose names contain the specified text, with an option to include hidden products.
     *
     * @param name the text to search for within product names
     * @param includeHidden whether to include hidden products in the search results
     * @param pageable pagination information
     * @return a Page of ProductEntity containing products whose names contain the specified text
     */
    @Query("SELECT p FROM ProductEntity p WHERE (:includeHidden = true OR p.isHidden = false) AND p.name LIKE CONCAT('%', :name, '%')")
    Page<ProductEntity> findProductsByNameContaining(@Param("name") String name, @Param("includeHidden") boolean includeHidden, Pageable pageable);

    /**
     * Finds products whose names start with the specified text, with an option to include hidden products.
     *
     * @param name the text to search for at the beginning of product names
     * @param includeHidden whether to include hidden products in the search results
     * @param pageable pagination information
     * @return a Page of ProductEntity containing products whose names start with the specified text
     */
    @Query("SELECT p FROM ProductEntity p WHERE (:includeHidden = true OR p.isHidden = false) AND p.name LIKE CONCAT(:name, '%')")
    Page<ProductEntity> findProductsByNameStartingWith(@Param("name") String name, @Param("includeHidden") boolean includeHidden, Pageable pageable);

    /**
     * Retrieves a paginated list of products associated with a specific supplier.
     *
     * @param id the unique identifier of the supplier whose products are to be retrieved
     * @param includeHidden a flag indicating whether to include hidden products (true to include, false to exclude)
     * @param pageable pagination information, including page number and size
     * @return a Page of ProductEntity containing products associated with the specified supplier
     */
    @Query("SELECT p FROM ProductEntity p WHERE (:includeHidden = true OR p.isHidden = false) AND p.id = :id")
    Page<ProductEntity> findProductsBySupplierId(@Param("id") Long id, @Param("includeHidden") boolean includeHidden, Pageable pageable);

    /**
     * Retrieves a paginated list of products by category ID, with an option to include hidden products.
     *
     * @param categoryId the unique identifier of the category
     * @param includeHidden whether to include hidden products in the search results
     * @param pageable pagination information
     * @return a Page of ProductEntity belonging to the specified category
     */
    @Query("SELECT p FROM ProductEntity p JOIN p.category c WHERE (:includeHidden = true OR p.isHidden = false) AND c.id = :id")
    Page<ProductEntity> findProductsByCategoryId(@Param("id") Long categoryId, @Param("includeHidden") boolean includeHidden, Pageable pageable);

    /**
     * Retrieves a paginated list of products by model ID, with an option to include hidden products.
     *
     * @param modelId the unique identifier of the model
     * @param includeHidden whether to include hidden products in the search results
     * @param pageable pagination information
     * @return a Page of ProductEntity associated with the specified model
     */
    @Query("SELECT p FROM ProductEntity p JOIN p.model m WHERE (:includeHidden = true OR p.isHidden = false) AND m.id = :id")
    Page<ProductEntity> findProductsByModelId(@Param("id") Long modelId, @Param("includeHidden") boolean includeHidden, Pageable pageable);

    /**
     * Retrieves a paginated list of all visible (non-hidden) products.
     *
     * @param pageable pagination information
     * @return a Page of ProductEntity containing only visible products
     */
    @Query("SELECT p FROM ProductEntity p WHERE p.isHidden = false")
    Page<ProductEntity> findAllVisibleProducts(Pageable pageable);

    /**
     * Updates the name of a product using a stored procedure.
     *
     * @param productId the unique identifier of the product
     * @param newName the new name for the product
     * @return an Optional containing the updated ProductEntity with the new name
     */
    @Modifying
    @Transactional
    @Query(value = "CALL update_product_name(:id, :name)", nativeQuery = true)
    Optional<ProductEntity> updateProductName(@Param("id") Long productId, @Param("name") String newName);

    /**
     * Updates the summary of a product using a stored procedure.
     *
     * @param productId the unique identifier of the product
     * @param newSummary the new summary for the product
     * @return an Optional containing the updated ProductEntity with the new summary
     */
    @Modifying
    @Transactional
    @Query(value = "CALL update_product_summary(:id, :summary)", nativeQuery = true)
    Optional<ProductEntity> updateProductSummary(@Param("id") Long productId, @Param("summary") String newSummary);

    /**
     * Updates the product code using a stored procedure.
     *
     * @param productId the unique identifier of the product
     * @param newCode the new product code
     * @return an Optional containing the updated ProductEntity with the new code
     */
    @Modifying
    @Transactional
    @Query(value = "CALL update_product_code(:id, :code)", nativeQuery = true)
    Optional<ProductEntity> updateProductCode(@Param("id") Long productId, @Param("code") String newCode);

    /**
     * Updates the barcode of a product using a stored procedure.
     *
     * @param productId the unique identifier of the product
     * @param newBarcode the new barcode for the product
     * @return an Optional containing the updated ProductEntity with the new barcode
     */
    @Modifying
    @Transactional
    @Query(value = "CALL update_product_barcode(:id, :barcode)", nativeQuery = true)
    Optional<ProductEntity> updateProductBarcode(@Param("id") Long productId, @Param("barcode") String newBarcode);

    /**
     * Updates the model association of a product using a stored procedure.
     *
     * @param productId the unique identifier of the product
     * @param modelId the unique identifier of the new model
     * @return an Optional containing the updated ProductEntity with the new model association
     */
    @Modifying
    @Transactional
    @Query(value = "CALL update_product_model(:productId, :modelId)", nativeQuery = true)
    Optional<ProductEntity> updateProductModel(@Param("productId") Long productId, @Param("modelId") Long modelId);

    /**
     * Updates the dimensions of a product using a stored procedure.
     *
     * @param productId the unique identifier of the product
     * @param dimensionsId the unique identifier of the new dimensions
     * @return an Optional containing the updated ProductEntity with the new dimensions
     */
    @Modifying
    @Transactional
    @Query(value = "CALL update_product_dimensions(:productId, :dimensionsId)", nativeQuery = true)
    Optional<ProductEntity> updateProductDimensions(@Param("productId") Long productId, @Param("dimensionsId") Long dimensionsId);


    /**
     * Adds a relationship between a product and a supplier by inserting a new record
     * into the product_supplier junction table.
     *
     * @param productId the ID of the product to associate with the supplier.
     * @param supplierId the ID of the supplier to associate with the product.
     * @return the number of rows affected by the insert operation.
     */
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO product_supplier (product_id, supplier_id) VALUES (:productId, :supplierId)", nativeQuery = true)
    int addProductSupplier(@Param("productId") Long productId, @Param("supplierId") Long supplierId);

    /**
     * Deletes the relationship between a product and a supplier by removing the
     * corresponding record from the product_supplier junction table.
     *
     * @param productId the ID of the product whose supplier relationship is to be removed.
     * @param supplierId the ID of the supplier whose relationship with the product is to be removed.
     * @return the number of rows affected by the delete operation.
     */
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM product_supplier WHERE product_id = :productId AND supplier_id = :supplierId", nativeQuery = true)
    int deleteProductSupplier(@Param("productId") Long productId, @Param("supplierId") Long supplierId);

    /**
     * Hides a product by setting its {@code isHidden} status to true.
     *
     * @param productId the ID of the product to hide.
     * @return the number of rows affected by the update operation.
     */
    @Modifying
    @Transactional
    @Query("UPDATE ProductEntity p SET p.isHidden = true WHERE p.id = :id")
    int hideProduct(@Param("id") Long productId);

    /**
     * Unhides a product by setting its {@code isHidden} status to false.
     *
     * @param productId the ID of the product to unhide.
     * @return the number of rows affected by the update operation.
     */
    @Modifying
    @Transactional
    @Query("UPDATE ProductEntity p SET p.isHidden = false WHERE p.id = :id")
    int unhideProduct(@Param("id") Long productId);

}
