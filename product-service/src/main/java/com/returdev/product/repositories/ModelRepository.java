package com.returdev.product.repositories;

import com.returdev.product.entities.ModelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service interface for managing models in the system.
 * Provides methods for retrieving, updating, saving, and deleting models.
 */
@Repository
public interface ModelRepository extends JpaRepository<ModelEntity, Long> {

    /**
     * Retrieves a paginated list of all active models.
     *
     * @param pageable pagination information
     * @return a Page of ModelEntity containing all active models
     */
    @Query("SELECT m FROM ModelEntity m WHERE m.isProductionActive = true")
    Page<ModelEntity> findAllActiveModels(Pageable pageable);

    /**
     * Retrieves a paginated list of models associated with a specific brand.
     * Allows for filtering to include only active models or all models.
     *
     * @param brandId the unique identifier of the brand whose models are to be retrieved
     * @param includeInactive whether to include inactive models (if true, retrieves all; if false, only active models)
     * @param pageable pagination information to control the page size and order
     * @return a Page of ModelEntity objects associated with the specified brand
     */
    @Query("SELECT m FROM ModelEntity m JOIN m.brand b WHERE (:includeInactive = true OR m.isProductionActive = true) AND b.id = :id")
    Page<ModelEntity> findModelsByBrandId(@Param("id") Long brandId, @Param("includeInactive") boolean includeInactive, Pageable pageable);

    /**
     * Updates the name of a model using a stored procedure.
     *
     * @param modelId the unique identifier of the model to update
     * @param newName the new name for the model
     * @return the updated ModelEntity
     */
    @Modifying
    @Transactional
    @Query(value = "CALL updateModelName(:id, :name)", nativeQuery = true)
    ModelEntity updateModelName(@Param("id") Long modelId, @Param("name") String newName);

    /**
     * Updates the summary of a model using a stored procedure.
     *
     * @param modelId the unique identifier of the model to update
     * @param newSummary the new summary for the model
     * @return the updated ModelEntity
     */
    @Modifying
    @Transactional
    @Query(value = "CALL updateModelSummary(:id, :summary)", nativeQuery = true)
    ModelEntity updateModelSummary(@Param("id") Long modelId, @Param("summary") String newSummary);

    /**
     * Updates the brand associated with a model using a stored procedure.
     *
     * @param modelId the unique identifier of the model
     * @param brandId the unique identifier of the brand
     * @return the updated ModelEntity
     */
    @Modifying
    @Transactional
    @Query(value = "CALL updateModelBrand(:modelId, :brandId)", nativeQuery = true)
    ModelEntity updateModelBrand(@Param("modelId") Long modelId, @Param("brandId") Long brandId);

    /**
     * Activates a model by setting its production status to active.
     *
     * @param modelId the unique identifier of the model to activate
     */
    @Modifying
    @Transactional
    @Query("UPDATE ModelEntity m SET m.isProductionActive = true WHERE m.id = :id ")
    void activateModel(@Param("id") Long modelId);

    /**
     * Inactivates a model identified by its ID.
     *
     * @param modelId the unique identifier of the model to inactivate
     */
    @Modifying
    @Transactional
    @Query("UPDATE ModelEntity m SET m.isProductionActive = false WHERE m.id = :id")
    void inactivateModel(@Param("id") Long modelId);

}

