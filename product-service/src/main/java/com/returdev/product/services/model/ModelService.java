package com.returdev.product.services.model;

import com.returdev.product.entities.ModelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing models in the system.
 * Provides methods for retrieving, updating, saving, and deleting model entities.
 */
public interface ModelService {

    /**
     * Retrieves a model by its unique ID.
     *
     * @param id the unique identifier of the model
     * @return an Optional containing the found ModelEntity, or empty if not found
     */
    ModelEntity getModelById(Long id);

    /**
     * Retrieves a paginated list of all models.
     *
     * @param pageable pagination information
     * @param includeInactive whether to include inactive models in the result
     * @return a Page of ModelEntity containing all models
     */
    Page<ModelEntity> getAllModels(Pageable pageable, boolean includeInactive);

    /**
     * Retrieves a paginated list of models associated with a specific brand.
     *
     * @param brandId the unique identifier of the brand
     * @param pageable pagination information
     * @return a Page of ModelEntity containing models associated with the brand
     */
    Page<ModelEntity> getModelsByBrandId(Long brandId, boolean includeInactive, Pageable pageable);

    /**
     * Updates the details of an existing model.
     *
     * @param model the ModelEntity with updated details
     * @return the updated ModelEntity
     */
    ModelEntity updateModel(ModelEntity model);

    /**
     * Updates the name of a model identified by its ID.
     *
     * @param id      the unique identifier of the model
     * @param newName the new name for the model
     * @return an Optional containing the updated ModelEntity with the new name
     */
    ModelEntity updateModelName(Long id, String newName);

    /**
     * Updates the summary of a model identified by its ID.
     *
     * @param id         the unique identifier of the model
     * @param newSummary the new summary for the model
     * @return an Optional containing the updated ModelEntity with the new summary
     */
    ModelEntity updateModelSummary(Long id, String newSummary);

    /**
     * Updates the brand dependency for the specified model.
     *
     * @param modelId the unique identifier of the model
     * @param brandId the unique identifier of the brand
     * @return an Optional containing the updated ModelEntity with the new brand association
     */
    ModelEntity updateModelBrand(Long modelId, Long brandId);

    /**
     * Saves a new model to the system.
     *
     * @param model the ModelEntity to be saved
     * @return the saved ModelEntity
     */
    ModelEntity saveModel(ModelEntity model);

    /**
     * Activates a model identified by its ID.
     *
     * @param id the unique identifier of the model
     */
    void activateModel(Long id);
    
    /**
     * Inactivates a model identified by its ID.
     *
     * @param id the unique identifier of the model
     */
    void inactivateModel(Long id);

}


