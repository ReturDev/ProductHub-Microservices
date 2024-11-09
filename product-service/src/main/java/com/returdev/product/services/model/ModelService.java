package com.returdev.product.services.model;

import com.returdev.product.entities.ModelEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    ModelEntity getModelById(@NotNull(message = "{validation.not_null.message}") Long id);

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
    Page<ModelEntity> getModelsByBrandId(
            @NotNull(message = "{validation.not_null.message}")
            Long brandId,
            boolean includeInactive,
            Pageable pageable
    );

    /**
     * Updates the details of an existing model.
     *
     * @param model the ModelEntity with updated details
     * @return the updated ModelEntity
     */
    ModelEntity updateModel(@Valid ModelEntity model);

    /**
     * Updates the name of a model identified by its ID.
     *
     * @param id the unique identifier of the model
     * @param newName the new name for the model
     * @return an Optional containing the updated ModelEntity with the new name
     */
    ModelEntity updateModelName(
            @NotNull(message = "{validation.not_null.message}")
            Long id,
            @NotBlank(message = "{validation.not_blank.message}")
            @Size(min = 3, max = 50, message = "{validation.size.message}")
            String newName
    );

    /**
     * Updates the summary of a model identified by its ID.
     *
     * @param id the unique identifier of the model
     * @param newSummary the new summary for the model
     * @return an Optional containing the updated ModelEntity with the new summary
     */
    ModelEntity updateModelSummary(
            @NotNull(message = "{validation.not_null.message}")
            Long id,
            @NotNull(message = "{validation.not_null.message}")
            @Size(max = 150, message = "{validation.size.max.message}")
            String newSummary
    );

    /**
     * Updates the brand dependency for the specified model.
     *
     * @param modelId the unique identifier of the model
     * @param brandId the unique identifier of the brand
     * @return an Optional containing the updated ModelEntity with the new brand association
     */
    ModelEntity updateModelBrand(
            @NotNull(message = "{validation.not_null.message}") Long modelId,
            @NotNull(message = "{validation.not_null.message}") Long brandId
    );

    /**
     * Saves a new model to the system.
     *
     * @param model the ModelEntity to be saved
     * @return the saved ModelEntity
     */
    ModelEntity saveModel(@Valid ModelEntity model);

    /**
     * Activates a model identified by its ID.
     *
     * @param id the unique identifier of the model
     */
    void activateModel(@NotNull(message = "{validation.not_null.message}") Long id);
    
    /**
     * Inactivates a model identified by its ID.
     *
     * @param id the unique identifier of the model
     */
    void inactivateModel(@NotNull(message = "{validation.not_null.message}") Long id);

}


