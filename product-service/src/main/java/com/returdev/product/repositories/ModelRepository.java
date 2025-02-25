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

import java.util.Optional;

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
     * @return an Optional containing the updated ModelEntity
     */
    @Query(value = "CALL updateModelName(:id, :name)", nativeQuery = true)
    Optional<ModelEntity> updateModelName(@Param("id") Long modelId, @Param("name") String newName);

    /**
     * Updates the summary of a model using a stored procedure.
     *
     * @param modelId the unique identifier of the model to update
     * @param newSummary the new summary for the model
     * @return an Optional containing  the updated ModelEntity
     */
    @Query(value = "CALL updateModelSummary(:id, :summary)", nativeQuery = true)
    Optional<ModelEntity> updateModelSummary(@Param("id") Long modelId, @Param("summary") String newSummary);

    /**
     * Updates the brand associated with a model using a stored procedure.
     *
     * @param modelId the unique identifier of the model
     * @param brandId the unique identifier of the brand
     * @return an Optional containing the updated ModelEntity
     */
    @Query(value = "CALL updateModelBrand(:modelId, :brandId)", nativeQuery = true)
    Optional<ModelEntity> updateModelBrand(@Param("modelId") Long modelId, @Param("brandId") Long brandId);

    /**
     * Activates a model by setting its {@code isProductionActive} status to true.
     *
     * @param modelId the ID of the model to activate.
     * @return the number of rows affected by the update operation.
     */
    @Modifying
    @Transactional
    @Query("UPDATE ModelEntity m SET m.isProductionActive = true WHERE m.id = :id")
    int activateModel(@Param("id") Long modelId);

    /**
     * Deactivates a model by setting its {@code isProductionActive} status to false.
     *
     * @param modelId the ID of the model to deactivate.
     * @return the number of rows affected by the update operation.
     */
    @Modifying
    @Transactional
    @Query("UPDATE ModelEntity m SET m.isProductionActive = false WHERE m.id = :id")
    int inactivateModel(@Param("id") Long modelId);

}

